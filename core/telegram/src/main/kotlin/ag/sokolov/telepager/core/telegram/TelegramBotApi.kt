package ag.sokolov.telepager.core.telegram

import ag.sokolov.telepager.core.model.BotDetails
import ag.sokolov.telepager.core.model.TelepagerError
import ag.sokolov.telepager.core.model.UserDetails
import ag.sokolov.telepager.core.result.Result

interface TelegramBotApi {
    suspend fun getBot(
        apiToken: String,
    ): Result<BotDetails, TelepagerError>

    suspend fun getUser(
        apiToken: String,
        userId: Long,
    ): Result<UserDetails, TelepagerError>

    suspend fun sendMessage(
        apiToken: String,
        userId: Long,
        text: String,
    ): Result<Unit, TelepagerError>
}
