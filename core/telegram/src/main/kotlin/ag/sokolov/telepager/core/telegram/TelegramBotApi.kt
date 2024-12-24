package ag.sokolov.telepager.core.telegram

import ag.sokolov.telepager.core.model.UserDetails
import ag.sokolov.telepager.core.result.Result
import ag.sokolov.telepager.core.telegram.retrofit.dto.UserDto

interface TelegramBotApi {
    suspend fun getBot(
        apiToken: String,
    ): Result<UserDto, TelegramBotApiError>

    suspend fun getUser(
        apiToken: String,
        userId: Long,
    ): Result<UserDetails, TelegramBotApiError>

    suspend fun sendMessage(
        apiToken: String,
        userId: Long,
        text: String,
    ): Result<Unit, TelegramBotApiError>
}
