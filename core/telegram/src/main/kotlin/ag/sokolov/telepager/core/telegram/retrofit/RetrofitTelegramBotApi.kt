package ag.sokolov.telepager.core.telegram.retrofit

import ag.sokolov.telepager.core.concurrency.CoroutineDispatchers.IO
import ag.sokolov.telepager.core.concurrency.Dispatcher
import ag.sokolov.telepager.core.result.Result
import ag.sokolov.telepager.core.result.Result.Failure
import ag.sokolov.telepager.core.result.Result.Success
import ag.sokolov.telepager.core.telegram.TelegramBotApi
import ag.sokolov.telepager.core.telegram.TelegramBotApiError
import ag.sokolov.telepager.core.telegram.TelegramBotApiError.NetworkError
import ag.sokolov.telepager.core.telegram.TelegramBotApiError.UnknownError
import ag.sokolov.telepager.core.telegram.dto.ChatMemberMemberDto
import ag.sokolov.telepager.core.telegram.dto.ErrorDto
import ag.sokolov.telepager.core.telegram.dto.SuccessDto
import ag.sokolov.telepager.core.telegram.dto.UpdateDto
import ag.sokolov.telepager.core.telegram.dto.UserDto
import ag.sokolov.telepager.core.telegram.mapTelegramBotApiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okio.IOException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject

internal class RetrofitTelegramBotApi @Inject constructor(
    private val json: Json,
    okHttpCallFactory: dagger.Lazy<Call.Factory>,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : TelegramBotApi {
    private val jsonMediaType = "application/json".toMediaType()

    private val botApi: RetrofitTelegramBotApiDeclaration =
        Retrofit.Builder()
            .baseUrl("https://api.telegram.org")
            .callFactory { okHttpCallFactory.get().newCall(it) }
            .addConverterFactory(json.asConverterFactory(jsonMediaType))
            .build()
            .create(RetrofitTelegramBotApiDeclaration::class.java)

    override suspend fun getMe(
        token: String,
    ): Result<UserDto, TelegramBotApiError> =
        withContext(ioDispatcher) {
            safeApiCall {
                botApi.getMe(token)
            }
        }

    override suspend fun getChatMember(
        token: String,
        chatId: Long,
        userId: Long,
    ): Result<ChatMemberMemberDto, TelegramBotApiError> =
        withContext(ioDispatcher) {
            safeApiCall {
                botApi.getChatMember(token, chatId, userId)
            }
        }

    override suspend fun getUpdates(
        token: String,
        timeout: Long?,
        offset: Long?,
        allowedUpdates: List<String>?,
    ): Result<List<UpdateDto>, TelegramBotApiError> =
        withContext(ioDispatcher) {
            safeApiCall {
                botApi.getUpdates(token, timeout, offset, allowedUpdates)
            }
        }

    override suspend fun sendMessage(
        token: String,
        chatId: Long,
        text: String,
    ): Result<Unit, TelegramBotApiError> =
        withContext(ioDispatcher) {
            safeApiCall {
                botApi.sendMessage(token, chatId, text)
            }
        }

    private suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<SuccessDto<T>>,
    ): Result<T, TelegramBotApiError> =
        try {
            val response = apiCall()
            val statusCode = response.code()

            if (response.isSuccessful) {
                Success(response.body()!!.result)
            } else {
                try {
                    val errorDto = json.decodeFromString<ErrorDto>(response.errorBody()!!.string())
                    val errorDescription = errorDto.description
                    Failure(mapTelegramBotApiError(statusCode, errorDescription))
                } catch (e: Exception) {
                    Failure(UnknownError(e.localizedMessage))
                }
            }
        } catch (e: IOException) {
            Failure(NetworkError(e.localizedMessage))
        } catch (e: Exception) {
            Failure(UnknownError(e.localizedMessage))
        }
}
