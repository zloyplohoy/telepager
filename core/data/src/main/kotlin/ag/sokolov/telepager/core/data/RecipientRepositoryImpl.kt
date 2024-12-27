package ag.sokolov.telepager.core.data

import ag.sokolov.telepager.core.concurrency.CoroutineDispatchers.IO
import ag.sokolov.telepager.core.concurrency.Dispatcher
import ag.sokolov.telepager.core.data.RecipientRepositoryError.InvalidToken
import ag.sokolov.telepager.core.data.RecipientRepositoryError.NetworkError
import ag.sokolov.telepager.core.data.RecipientRepositoryError.UnknownError
import ag.sokolov.telepager.core.database.dao.BotDao
import ag.sokolov.telepager.core.database.dao.RecipientDao
import ag.sokolov.telepager.core.database.entity.asExternalModel
import ag.sokolov.telepager.core.model.Recipient
import ag.sokolov.telepager.core.result.Result
import ag.sokolov.telepager.core.result.Result.Failure
import ag.sokolov.telepager.core.result.Result.Success
import ag.sokolov.telepager.core.telegram.TelegramBotApi
import ag.sokolov.telepager.core.telegram.TelegramBotApiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipientRepositoryImpl @Inject constructor(
    private val botDao: BotDao,
    private val recipientDao: RecipientDao,
    private val telegramBotApi: TelegramBotApi,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : RecipientRepository {
    override fun getRecipients(): Flow<List<Recipient>> =
        recipientDao.getRecipients()
            .distinctUntilChanged()
            .map { it.map { it.asExternalModel() } }
            .onStart { updateDetails() }

    override fun getRecipientIds(): Flow<List<Long>> =
        recipientDao.getRecipients()
            .distinctUntilChanged()
            .map { recipients ->
                recipients.map { it.id }
            }

    override suspend fun addRecipient(
        id: Long,
        firstName: String,
        lastName: String?,
        username: String?,
    ): Result<Nothing, RecipientRepositoryError> =
        withContext(ioDispatcher) {
            recipientDao.addRecipient(
                id = id,
                firstName = firstName,
                lastName = lastName,
                username = username
            )
            Success()
        }

    override suspend fun updateDetails(): Result<Nothing, RecipientRepositoryError> =
        withContext(ioDispatcher) {
            val botToken = botDao.getBotToken().firstOrNull()

            if (botToken == null) {
                Failure(InvalidToken)
            } else {
                recipientDao.getRecipients()
                    .firstOrNull()
                    ?.map { updateDetails(botToken = botToken, recipientId = it.id) }
                Success()
            }
        }

    private suspend fun updateDetails(
        botToken: String,
        recipientId: Long,
    ): Result<Nothing, RecipientRepositoryError> =
        when (val getUserResult = telegramBotApi.getUser(botToken, recipientId)) {
            is Success -> {
                val user = getUserResult.data!!

                if (user.firstName == "") {
                    recipientDao.setDetails(
                        id = recipientId,
                        firstName = "Deleted Account",
                        lastName = null,
                        username = null
                    )
                } else {
                    recipientDao.setDetails(
                        id = recipientId,
                        firstName = user.firstName,
                        lastName = user.lastName,
                        username = user.username
                    )
                }
                Success()
            }

            is Failure -> {
                when (getUserResult.error) {
                    is TelegramBotApiError.Unauthorized -> {
                        botDao.setIsTokenValid(false)
                        Failure(InvalidToken)
                    }

                    is TelegramBotApiError.Forbidden.BotWasBlockedByTheUser -> {
                        recipientDao.setIsBotBlocked(id = recipientId, isBotBlocked = true)
                        Success()
                    }

                    is TelegramBotApiError.BadRequest.ChatNotFound -> {
                        recipientDao.setDetails(
                            id = recipientId,
                            firstName = "Unknown Account",
                            lastName = null,
                            username = null
                        )
                        Success()
                    }

                    is TelegramBotApiError.NetworkError -> {
                        Failure(NetworkError)
                    }

                    else -> Failure(UnknownError)
                }
            }
        }


    override suspend fun deleteRecipient(id: Long): Result<Nothing, RecipientRepositoryError> =
        withContext(ioDispatcher) {
            recipientDao.deleteRecipient(id)
            Success()
        }
}
