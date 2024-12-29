package ag.sokolov.telepager.core.telegram

import ag.sokolov.telepager.core.result.Result
import ag.sokolov.telepager.core.telegram.retrofit.dto.ChatFullInfoDto
import ag.sokolov.telepager.core.telegram.retrofit.dto.UpdateDto
import ag.sokolov.telepager.core.telegram.retrofit.dto.UserDto

interface TelegramBotApi {
    suspend fun getMe(
        token: String,
    ): Result<UserDto, TelegramBotApiError>

    suspend fun getChat(
        token: String,
        chatId: Long,
    ): Result<ChatFullInfoDto, TelegramBotApiError>

    suspend fun getUpdates(
        token: String,
        timeout: Long? = null,
        offset: Long? = null,
        allowedUpdates: List<String>? = null,
    ): Result<List<UpdateDto>, TelegramBotApiError>

    suspend fun sendMessage(
        token: String,
        userId: Long,
        text: String,
    ): Result<Unit, TelegramBotApiError>
}
