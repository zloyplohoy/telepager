package ag.sokolov.telepager.core.telegram

import ag.sokolov.telepager.core.model.BotDetails
import ag.sokolov.telepager.core.model.TelepagerError
import ag.sokolov.telepager.core.model.TelepagerResult

interface TelegramBotApi {
    suspend fun getTelegramBot(apiToken: String): TelepagerResult<BotDetails, TelepagerError>
}
