package ag.sokolov.telepager.core.data

import ag.sokolov.telepager.core.concurrency.CoroutineDispatchers.IO
import ag.sokolov.telepager.core.concurrency.Dispatcher
import ag.sokolov.telepager.core.data.RepositoryError.InvalidToken
import ag.sokolov.telepager.core.data.RepositoryError.TokenNotSet
import ag.sokolov.telepager.core.data.RepositoryError.UnknownError
import ag.sokolov.telepager.core.database.dao.BotDao
import ag.sokolov.telepager.core.database.dao.RecipientDao
import ag.sokolov.telepager.core.database.entity.asExternalModel
import ag.sokolov.telepager.core.model.Recipient
import ag.sokolov.telepager.core.result.Result
import ag.sokolov.telepager.core.result.Result.Failure
import ag.sokolov.telepager.core.result.Result.Success
import ag.sokolov.telepager.core.telegram.TelegramBotApi
import ag.sokolov.telepager.core.telegram.TelegramBotApiError
import ag.sokolov.telepager.core.telegram.TelegramBotApiError.Unauthorized
import ag.sokolov.telepager.core.telegram.retrofit.dto.UpdateDto
import ag.sokolov.telepager.core.telegram.retrofit.dto.UserDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.Nothing

class RecipientRepositoryImpl @Inject constructor(
    private val botDao: BotDao,
    private val recipientDao: RecipientDao,
    private val telegramBotApi: TelegramBotApi,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : RecipientRepository {
    private companion object {
        const val RECIPIENT_REGISTRATION_BOT_API_LONG_POLLING_TIMEOUT: Long = 30
    }

    override fun getRecipients(): Flow<List<Recipient>> =
        recipientDao.getRecipients()
            .distinctUntilChanged()
            .map { it.map { it.asExternalModel() } }
            .onStart { updateDetails() }

    // TODO: Add a dedicated database query
    override fun getRecipientIds(): Flow<List<Long>> =
        recipientDao.getRecipients()
            .distinctUntilChanged()
            .map { it.map { it.id } }

    override suspend fun registerRecipient(
        verificationCode: String,
    ): Result<Nothing, RepositoryError> =
        withContext(ioDispatcher) {
            val botToken = getBotToken() ?: return@withContext Failure(TokenNotSet)

            var offset: Long? = null
            var noUpdatesCount = 0

            while (true) {
                when (val getUpdatesResult = getBotUpdates(botToken, offset = offset)) {
                    is Success -> {
                        val updates = getUpdatesResult.data!!

                        if (updates.isEmpty()) {
                            noUpdatesCount++
                        } else {
                            updates.findVerifiedRecipient(verificationCode)
                                ?.also { recipient ->
                                    saveRecipient(recipient)
                                    return@withContext Success()
                                }

                            offset = updates.getNextOffset() ?: offset
                        }
                    }

                    is Failure -> {
                        if (getUpdatesResult.error is Unauthorized) invalidateBotToken()
                        return@withContext getUpdatesResult.asRepositoryResult()
                    }
                }
            }

            // Prevent compiler errors due to while loop potentially returning nothing
            Failure(UnknownError)
        }

    private suspend fun getBotToken(): String? =
        botDao.getBotToken().first()

    private suspend fun invalidateBotToken() =
        botDao.setIsTokenValid(false)

    private fun List<UpdateDto>.getNextOffset(): Long? =
        this.lastOrNull()?.updateId?.plus(1)

    private suspend fun getBotUpdates(
        token: String,
        offset: Long? = null,
    ): Result<List<UpdateDto>, TelegramBotApiError> =
        telegramBotApi.getUpdates(
            token,
            timeout = RECIPIENT_REGISTRATION_BOT_API_LONG_POLLING_TIMEOUT,
            offset = offset
        )

    private fun List<UpdateDto>.findVerifiedRecipient(verificationCode: String): Recipient? =
        this
            .find { it.isValidVerificationMessage(verificationCode) }
            ?.run { this.message!!.from!!.asRecipient() }

    private fun UpdateDto.isValidVerificationMessage(verificationCode: String): Boolean =
        this.message?.text == "/start $verificationCode"

    private fun saveRecipient(recipient: Recipient) =
        recipientDao.addRecipient(
            id = recipient.id,
            firstName = recipient.firstName,
            lastName = recipient.lastName,
            username = recipient.username
        )

    private fun UserDto.asRecipient() =
        Recipient(
            id = id,
            firstName = firstName,
            lastName = lastName,
            username = username,
            isDeleted = false,
            isBotBlocked = false,
        )

    override suspend fun updateDetails(): Result<Nothing, RepositoryError> =
        withContext(ioDispatcher) {
            val botToken = botDao.getBotToken().firstOrNull()
            val recipientIds = recipientDao.getRecipients().firstOrNull()?.map { it.id }

            if (botToken == null) {
                Failure(TokenNotSet)
            } else if (recipientIds == null) {
                Success()
            } else {
                when (val getMeResult = telegramBotApi.getMe(botToken)) {
                    is Success -> {
                        recipientIds.forEach { updateDetails(botToken, it) }
                        Success()
                    }

                    is Failure -> {
                        when (getMeResult.error) {
                            is TelegramBotApiError.Unauthorized -> {
                                botDao.setIsTokenValid(false)
                                Failure(InvalidToken)
                            }

                            else -> {
                                getMeResult.asRepositoryResult()
                            }
                        }
                    }
                }
            }
        }

    private suspend fun updateDetails(
        botToken: String,
        recipientId: Long,
    ) =
        when (val getChatResult = telegramBotApi.getChat(botToken, recipientId)) {
            is Success -> {
                val user = getChatResult.data!!

                if (user.firstName == "") {
                    recipientDao.setIsDeleted(id = user.id, isDeleted = true)
                }

                recipientDao.setDetails(
                    id = recipientId,
                    firstName = user.firstName!!,
                    lastName = user.lastName,
                    username = user.username
                )
            }

            is Failure -> {
                when (getChatResult.error) {
                    is TelegramBotApiError.Unauthorized -> {
                        botDao.setIsTokenValid(false)
                    }

                    is TelegramBotApiError.Forbidden.BotWasBlockedByTheUser -> {
                        recipientDao.setIsBotBlocked(id = recipientId, isBotBlocked = true)
                    }

                    is TelegramBotApiError.BadRequest.ChatNotFound -> {
                        recipientDao.setDetails(
                            id = recipientId,
                            firstName = "Unknown Account",
                            lastName = null,
                            username = null
                        )
                    }

                    else -> {}
                }
            }
        }


    override suspend fun deleteRecipient(id: Long): Result<Nothing, RepositoryError> =
        withContext(ioDispatcher) {
            recipientDao.deleteRecipient(id)
            Success()
        }
}
