package ag.sokolov.telepager.core.telegram

import ag.sokolov.telepager.core.result.Result
import ag.sokolov.telepager.core.telegram.retrofit.dto.ChatFullInfoDto
import ag.sokolov.telepager.core.telegram.retrofit.dto.UserDto

interface TelegramBotApi {
    suspend fun getMe(
        token: String,
    ): Result<UserDto, TelegramBotApiError>

    suspend fun getChat(
        token: String,
        userId: Long,
    ): Result<ChatFullInfoDto, TelegramBotApiError>

    suspend fun sendMessage(
        token: String,
        userId: Long,
        text: String,
    ): Result<Unit, TelegramBotApiError>
}
