package ag.sokolov.telepager.core.domain.domain

import ag.sokolov.telepager.core.data.BotRepository
import ag.sokolov.telepager.core.model.Bot
import ag.sokolov.telepager.core.result.Result
import ag.sokolov.telepager.core.result.Result.Failure
import ag.sokolov.telepager.core.result.Result.Success
import ag.sokolov.telepager.core.telegram.TelegramBotApi
import ag.sokolov.telepager.core.telegram.TelegramBotApiError.Unauthorized
import ag.sokolov.telepager.core.telegram.dto.asExternalModel
import kotlinx.coroutines.flow.first

class UpdateBotUseCase(
    val botRepository: BotRepository,
    val telegramBotApi: TelegramBotApi,
) {
    suspend operator fun invoke(): Result<Nothing, String> {
        val botToken = botRepository.getBotToken().first()?.token ?: return Failure("Token not set")
        val getMeResponse = telegramBotApi.getMe(botToken)

        when (getMeResponse) {
            is Success -> {
                botRepository.updateBot(getMeResponse.data!!.asExternalModel<Bot>())
                return Success()
            }

            is Failure -> {
                if (getMeResponse.error is Unauthorized) botRepository.invalidateBotToken()
                return Failure("Failed to update bot")
            }
        }
    }
}
