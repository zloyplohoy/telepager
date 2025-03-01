package ag.sokolov.telepager.core.domain.domain

import ag.sokolov.telepager.core.data.BotRepository
import ag.sokolov.telepager.core.data.RecipientRepository
import ag.sokolov.telepager.core.model.BotToken
import ag.sokolov.telepager.core.result.Result
import ag.sokolov.telepager.core.result.Result.Failure
import ag.sokolov.telepager.core.result.Result.Success
import ag.sokolov.telepager.core.telegram.TelegramBotApi
import ag.sokolov.telepager.core.telegram.TelegramBotApiError.Unauthorized
import ag.sokolov.telepager.core.telegram.dto.asExternalModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateRecipientsUseCase @Inject constructor(
    val botRepository: BotRepository,
    val recipientRepository: RecipientRepository,
    val telegramBotApi: TelegramBotApi,
) {
    suspend operator fun invoke(): Result<Nothing, String> {
        val botToken: BotToken = getBotToken() ?: return Failure("Token not set")
        if (!botToken.isValid) return Failure("Token invalid")

        getRecipientIdList()
            .forEach { updateRecipient(botToken = botToken.token, recipientId = it) }

        return Success()
    }

    private suspend fun getBotToken(): BotToken? =
        botRepository.getBotToken().first()

    private suspend fun getRecipientIdList(): List<Long> =
        recipientRepository.getRecipientIdList().first()

    private suspend fun updateRecipient(
        botToken: String,
        recipientId: Long,
    ) {
        val getChatMemberResult = telegramBotApi.getChatMember(botToken, recipientId, recipientId)

        when (getChatMemberResult) {
            is Success -> recipientRepository.updateRecipient(getChatMemberResult.data!!.user.asExternalModel())
            is Failure -> if (getChatMemberResult.error is Unauthorized) botRepository.invalidateBotToken()
            // TODO: Implement bot blocked by user logic
        }
    }
}
