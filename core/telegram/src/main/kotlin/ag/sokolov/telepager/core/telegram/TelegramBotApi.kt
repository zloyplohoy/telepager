package ag.sokolov.telepager.core.telegram

import ag.sokolov.telepager.core.model.BotDetails
import ag.sokolov.telepager.core.model.TelepagerError
import ag.sokolov.telepager.core.model.TelepagerResult
import ag.sokolov.telepager.core.model.UserDetails

interface TelegramBotApi {
    suspend fun getBot(
        apiToken: String,
    ): TelepagerResult<BotDetails, TelepagerError>

    suspend fun getUser(
        apiToken: String,
        userId: Long,
    ): TelepagerResult<UserDetails, TelepagerError>

    suspend fun sendMessage(
        apiToken: String,
        userId: Long,
        text: String,
    ): TelepagerResult<Unit, TelepagerError>
}
