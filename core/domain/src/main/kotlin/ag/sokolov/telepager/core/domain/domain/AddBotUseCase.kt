package ag.sokolov.telepager.core.domain.domain

import ag.sokolov.telepager.core.data.BotRepository
import ag.sokolov.telepager.core.model.Bot
import ag.sokolov.telepager.core.result.Result
import ag.sokolov.telepager.core.result.Result.Failure
import ag.sokolov.telepager.core.result.Result.Success
import ag.sokolov.telepager.core.telegram.TelegramBotApi
import ag.sokolov.telepager.core.telegram.retrofit.dto.asExternalModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddBotUseCase @Inject constructor(
    val botRepository: BotRepository,
    val telegramBotApi: TelegramBotApi,
) {
    suspend operator fun invoke(token: String): Result<Nothing, String> {
        val getMeResponse = telegramBotApi.getMe(token)

        when (getMeResponse) {
            is Success -> {
                botRepository.setBotToken(token)
                botRepository.setBot(getMeResponse.data!!.asExternalModel<Bot>())

                return Success()
            }

            is Failure -> return Failure("Failed to add bot")
        }
    }
}
