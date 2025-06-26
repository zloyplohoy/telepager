package ag.sokolov.telepager.core.telegram.ktor

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
import ag.sokolov.telepager.core.telegram.dto.ResponseDto
import ag.sokolov.telepager.core.telegram.dto.UpdateDto
import ag.sokolov.telepager.core.telegram.dto.UserDto
import ag.sokolov.telepager.core.telegram.mapTelegramBotApiError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.nio.channels.UnresolvedAddressException
import javax.inject.Inject

internal class KtorTelegramBotApi @Inject constructor(
    private val json: Json,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : TelegramBotApi {
    private val client = HttpClient(CIO) {
        engine { dispatcher = ioDispatcher }
        defaultRequest { url("https://api.telegram.org") }
        install(ContentNegotiation) { json(json) }
        install(Resources)
    }

    // https://core.telegram.org/bots/api#getme
    override suspend fun getMe(token: String): Result<UserDto, TelegramBotApiError> =
        safeApiCall {
            client.get(
                Bot.GetMe(
                    Bot(token.asUrlToken())
                )
            )
        }

    // https://core.telegram.org/bots/api#getchatmember
    override suspend fun getChatMember(
        token: String,
        chatId: Long,
        userId: Long,
    ): Result<ChatMemberMemberDto, TelegramBotApiError> =
        safeApiCall {
            client.get(
                Bot.GetChatMember(
                    Bot(token.asUrlToken()),
                    chatId,
                    userId
                )
            )
        }

    // https://core.telegram.org/bots/api#getupdates
    override suspend fun getUpdates(
        token: String,
        timeout: Long?,
        offset: Long?,
        allowedUpdates: List<String>?,
    ): Result<List<UpdateDto>, TelegramBotApiError> =
        safeApiCall {
            client.get(
                Bot.GetUpdates(
                    Bot(token.asUrlToken()),
                    timeout,
                    offset,
                    json.encodeToString(allowedUpdates)
                )
            )
        }

    // https://core.telegram.org/bots/api#sendmessage
    override suspend fun sendMessage(
        token: String,
        chatId: Long,
        text: String,
    ): Result<Unit, TelegramBotApiError> =
        safeApiCall {
            client.post(
                Bot.SendMessage(
                    Bot(token.asUrlToken()),
                    chatId,
                    text
                )
            )
        }

    private suspend inline fun <reified T> safeApiCall(
        crossinline apiCall: suspend () -> HttpResponse,
    ): Result<T, TelegramBotApiError> =
        withContext(ioDispatcher) {
            try {
                val apiResponse = apiCall()
                val statusCode = apiResponse.status.value
                val errorDescription = apiResponse.body<ResponseDto<Unit>>().description

                if (statusCode == 200) {
                    Success(apiResponse.body<ResponseDto<T>>().result)
                } else {
                    Failure(mapTelegramBotApiError(statusCode, errorDescription))
                }
            } catch (e: UnresolvedAddressException) {
                Failure(NetworkError(e.localizedMessage))
            } catch (e: Exception) {
                Failure(UnknownError(e.localizedMessage))
            }
        }

    private fun String.asUrlToken(): String = "bot$this"
}
