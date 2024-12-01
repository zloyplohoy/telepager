package ag.sokolov.telepager.core.telegram

import ag.sokolov.telepager.core.model.TelegramBotInfo
import ag.sokolov.telepager.core.model.TelepagerError
import ag.sokolov.telepager.core.model.TelepagerResult
import ag.sokolov.telepager.core.telegram.retrofit.RetrofitTelegramBotApi
import ag.sokolov.telepager.core.telegram.retrofit.dto.UserDto
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okio.IOException
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject

internal class TelegramBotApiImpl @Inject constructor(
    json: Json,
    okHttpCallFactory: dagger.Lazy<Call.Factory>,
) : TelegramBotApi {
    private val jsonMediaType = "application/json".toMediaType()

    private val telegramBotApiService: RetrofitTelegramBotApi =
        Retrofit.Builder()
            .baseUrl("https://api.telegram.org")
            .callFactory { okHttpCallFactory.get().newCall(it) }
            .addConverterFactory(json.asConverterFactory(jsonMediaType))
            .build()
            .create(RetrofitTelegramBotApi::class.java)

    override suspend fun getTelegramBot(apiToken: String): TelepagerResult<TelegramBotInfo, TelepagerError> =
        try {
            TelepagerResult.Success(telegramBotApiService.getMe(apiToken).result.toBotInfo())
        } catch (e: IOException) {
            TelepagerResult.Failure(TelepagerError.NetworkError)
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> TelepagerResult.Failure(TelepagerError.BotApiTokenInvalid)
                else -> TelepagerResult.Failure(TelepagerError.UnhandledError)
            }
        }

    private fun UserDto.toBotInfo(): TelegramBotInfo =
        TelegramBotInfo(
            name = this.firstName,
            username = this.username!! // username guaranteed for bots
        )
}
