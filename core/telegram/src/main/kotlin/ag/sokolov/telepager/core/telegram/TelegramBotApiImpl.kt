package ag.sokolov.telepager.core.telegram

import ag.sokolov.telepager.core.model.BotDetails
import ag.sokolov.telepager.core.model.TelepagerError
import ag.sokolov.telepager.core.model.TelepagerError.BotError
import ag.sokolov.telepager.core.model.TelepagerError.NetworkError
import ag.sokolov.telepager.core.model.TelepagerError.UnknownError
import ag.sokolov.telepager.core.model.TelepagerResult
import ag.sokolov.telepager.core.model.TelepagerResult.Failure
import ag.sokolov.telepager.core.model.TelepagerResult.Success
import ag.sokolov.telepager.core.model.UserDetails
import ag.sokolov.telepager.core.telegram.retrofit.RetrofitTelegramBotApi
import ag.sokolov.telepager.core.telegram.retrofit.dto.ErrorDto
import ag.sokolov.telepager.core.telegram.retrofit.dto.ResponseDto
import ag.sokolov.telepager.core.telegram.retrofit.dto.asBotDetails
import ag.sokolov.telepager.core.telegram.retrofit.dto.asUserDetails
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

    override suspend fun getBot(
        apiToken: String,
    ): TelepagerResult<BotDetails, TelepagerError> =
        safeApiCall(
            apiCall = { botApi.getMe(apiToken) },
            transform = { it.asBotDetails() }
        )

    override suspend fun getUser(
        apiToken: String,
        userId: Long,
    ): TelepagerResult<UserDetails, TelepagerError> =
        safeApiCall(
            apiCall = { botApi.getChat(apiToken, userId) },
            transform = { it.asUserDetails() }
        )

    override suspend fun sendMessage(
        apiToken: String,
        userId: Long,
        text: String,
    ): TelepagerResult<Unit, TelepagerError> =
        safeApiCall(
            apiCall = { botApi.sendMessage(apiToken, userId, text) },
            transform = { it }
        )

    private suspend fun <T, R> safeApiCall(
        apiCall: suspend () -> Response<ResponseDto<T>>,
        transform: (T) -> R,
    ): TelepagerResult<R, TelepagerError> =
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                Success(transform(response.body()!!.result))
            } else {
                val errorBody = response.errorBody()?.string()
                val error = parseApiErrorBody(errorBody)
                error?.let {
                    Failure(BotError(it.description))
                } ?: Failure(UnknownError("Unknown Telegram API error"))
            }
        } catch (e: IOException) {
            Failure(NetworkError(e.localizedMessage))
        } catch (e: Exception) {
            Failure(UnknownError(e.localizedMessage))
        }

    private fun parseApiErrorBody(errorBody: String?): ErrorDto? =
        try {
            errorBody?.let { json.decodeFromString<ErrorDto>(it) }
        } catch (_: Exception) {
            null
        }
}
