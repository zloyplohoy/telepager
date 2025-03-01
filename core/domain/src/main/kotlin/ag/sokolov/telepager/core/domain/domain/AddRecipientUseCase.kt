package ag.sokolov.telepager.core.domain.domain

import ag.sokolov.telepager.core.data.BotRepository
import ag.sokolov.telepager.core.data.RecipientRepository
import ag.sokolov.telepager.core.model.Recipient
import ag.sokolov.telepager.core.result.Result
import ag.sokolov.telepager.core.result.Result.Failure
import ag.sokolov.telepager.core.result.Result.Success
import ag.sokolov.telepager.core.telegram.TelegramBotApi
import ag.sokolov.telepager.core.telegram.TelegramBotApiError
import ag.sokolov.telepager.core.telegram.TelegramBotApiError.Unauthorized
import ag.sokolov.telepager.core.telegram.dto.MessageDto
import ag.sokolov.telepager.core.telegram.dto.UpdateDto
import ag.sokolov.telepager.core.telegram.dto.UserDto
import ag.sokolov.telepager.core.telegram.dto.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddRecipientUseCase @Inject constructor(
    val botRepository: BotRepository,
    val recipientRepository: RecipientRepository,
    val telegramBotApi: TelegramBotApi,
) {
    companion object {
        const val RECIPIENT_REGISTRATION_BOT_API_LONG_POLLING_TIMEOUT: Long = 5L
    }

    operator fun invoke(): Flow<AddRecipientState> = flow {
        val botToken: String =
            getBotToken() ?: run { emit(AddRecipientState.Failure); return@flow }
        val botUsername: String =
            getBotUsername() ?: run { emit(AddRecipientState.Failure); return@flow }

        val verificationCode = generateVerificationCode()
        val verificationTgUrl = getTgVerificationUrl(botUsername, verificationCode)

        emit(AddRecipientState.Started(verificationTgUrl))

        val registrationResult =
            registerRecipient(botToken = botToken, verificationCode = verificationCode)

        emit(registrationResult)
    }

    private suspend fun getBotToken(): String? =
        botRepository.getBotToken().first()?.token

    private suspend fun getBotUsername(): String? =
        botRepository.getBot().first()?.username

    private fun generateVerificationCode(): String =
        "%06d".format((0..999999).random())

    private fun getTgVerificationUrl(botUsername: String, verificationCode: String): String =
        "tg://resolve?domain=$botUsername&start=$verificationCode"

    private suspend fun registerRecipient(
        botToken: String,
        verificationCode: String,
    ): AddRecipientState {
        var offset: Long? = null

        while (true) {
            when (val getUpdatesResult = getUpdates(botToken = botToken, offset = offset)) {
                is Success -> {
                    val updates = getUpdatesResult.data!!

                    updates.findVerifiedUser(verificationCode)
                        ?.asExternalModel<Recipient>()
                        ?.also {
                            recipientRepository.addRecipient(it)
                            return AddRecipientState.Success
                        }

                    offset = updates.getNextOffset()
                }

                is Failure -> {
                    if (getUpdatesResult.error is Unauthorized) botRepository.invalidateBotToken()
                    return AddRecipientState.Failure
                }
            }
        }
    }

    private suspend fun getUpdates(
        botToken: String,
        offset: Long? = null,
    ): Result<List<UpdateDto>, TelegramBotApiError> =
        telegramBotApi.getUpdates(
            token = botToken,
            timeout = RECIPIENT_REGISTRATION_BOT_API_LONG_POLLING_TIMEOUT,
            offset = offset
        )

    private fun List<UpdateDto>.findVerifiedUser(verificationCode: String): UserDto? =
        this
            .find { it.message.isValidVerificationMessage(verificationCode) }
            ?.run { this.message!!.from!! }

    private fun MessageDto?.isValidVerificationMessage(verificationCode: String): Boolean =
        this?.text == "/start $verificationCode"

    private fun List<UpdateDto>.getNextOffset(): Long? =
        this.lastOrNull()?.updateId?.plus(1)
}
