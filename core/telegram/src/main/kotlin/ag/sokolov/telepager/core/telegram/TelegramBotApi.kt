package ag.sokolov.telepager.core.telegram

import ag.sokolov.telepager.core.model.TelegramBotInfo
import ag.sokolov.telepager.core.model.TelepagerError
import ag.sokolov.telepager.core.model.TelepagerResult

interface TelegramBotApi {
    suspend fun getTelegramBot(apiToken: String): TelepagerResult<TelegramBotInfo, TelepagerError>
}
