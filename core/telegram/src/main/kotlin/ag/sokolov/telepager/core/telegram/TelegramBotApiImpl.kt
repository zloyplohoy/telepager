package ag.sokolov.telepager.core.telegram

import ag.sokolov.telepager.core.result.Result
import ag.sokolov.telepager.core.result.Result.Failure
import ag.sokolov.telepager.core.result.Result.Success
import ag.sokolov.telepager.core.telegram.TelegramBotApiError.BadRequest
import ag.sokolov.telepager.core.telegram.TelegramBotApiError.Forbidden
import ag.sokolov.telepager.core.telegram.TelegramBotApiError.NetworkError
import ag.sokolov.telepager.core.telegram.TelegramBotApiError.Unauthorized
import ag.sokolov.telepager.core.telegram.TelegramBotApiError.UnknownError
import ag.sokolov.telepager.core.telegram.retrofit.RetrofitTelegramBotApi
import ag.sokolov.telepager.core.telegram.retrofit.dto.ChatFullInfoDto
import ag.sokolov.telepager.core.telegram.retrofit.dto.ErrorDto
import ag.sokolov.telepager.core.telegram.retrofit.dto.ResponseDto
import ag.sokolov.telepager.core.telegram.retrofit.dto.UpdateDto
import ag.sokolov.telepager.core.telegram.retrofit.dto.UserDto
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okio.IOException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject

internal class TelegramBotApiImpl @Inject constructor(
    private val json: Json,
    okHttpCallFactory: dagger.Lazy<Call.Factory>,
) : TelegramBotApi {
    private val jsonMediaType = "application/json".toMediaType()

    private val botApi: RetrofitTelegramBotApi =
        Retrofit.Builder()
            .baseUrl("https://api.telegram.org")
            .callFactory { okHttpCallFactory.get().newCall(it) }
            .addConverterFactory(json.asConverterFactory(jsonMediaType))
            .build()
            .create(RetrofitTelegramBotApi::class.java)

    override suspend fun getMe(
        token: String,
    ): Result<UserDto, TelegramBotApiError> =
        safeApiCall { botApi.getMe(token) }

    override suspend fun getChat(
        token: String,
        chatId: Long,
    ): Result<ChatFullInfoDto, TelegramBotApiError> =
        safeApiCall { botApi.getChat(token, chatId) }

    override suspend fun getUpdates(
        token: String,
        timeout: Long?,
        offset: Long?,
        allowedUpdates: List<String>?,
    ): Result<List<UpdateDto>, TelegramBotApiError> =
        safeApiCall { botApi.getUpdates(token, timeout, offset, allowedUpdates) }

    override suspend fun sendMessage(
        token: String,
        userId: Long,
        text: String,
    ): Result<Unit, TelegramBotApiError> =
        safeApiCall { botApi.sendMessage(token, userId, text) }

    private suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<ResponseDto<T>>,
    ): Result<T, TelegramBotApiError> =
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                Success(response.body()!!.result)
            } else {
                Failure(getTelegramApiError<T>(response))
            }
        } catch (e: IOException) {
            Failure(NetworkError(e.localizedMessage))
        } catch (e: Exception) {
            Failure(UnknownError(e.localizedMessage))
        }

    private fun <T> getTelegramApiError(response: Response<ResponseDto<T>>): TelegramBotApiError =
        try {
            val errorDto = json.decodeFromString<ErrorDto>(response.errorBody()!!.string())
            when (response.code()) {
                401 -> Unauthorized
                403 -> Forbidden.BotWasBlockedByTheUser
                400 -> {
                    when (errorDto.description) {
                        "Bad Request: chat not found" -> BadRequest.ChatNotFound
                        "Bad Request: message text is empty" -> BadRequest.MessageTextIsEmpty
                        else -> UnknownError(errorDto.description)
                    }
                }

                else -> UnknownError(errorDto.description)
            }
        } catch (_: Exception) {
            UnknownError("Could not parse error body")
        }
}
