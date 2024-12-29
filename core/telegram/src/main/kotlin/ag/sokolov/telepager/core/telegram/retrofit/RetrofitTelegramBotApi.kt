package ag.sokolov.telepager.core.telegram.retrofit

import ag.sokolov.telepager.core.telegram.retrofit.dto.ChatFullInfoDto
import ag.sokolov.telepager.core.telegram.retrofit.dto.ResponseDto
import ag.sokolov.telepager.core.telegram.retrofit.dto.UpdateDto
import ag.sokolov.telepager.core.telegram.retrofit.dto.UserDto
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitTelegramBotApi {
    // https://core.telegram.org/bots/api#getme
    @GET("/bot{token}/getMe")
    suspend fun getMe(
        @Path("token") token: String,
    ): Response<ResponseDto<UserDto>>

    // https://core.telegram.org/bots/api#getchat
    @GET("/bot{token}/getChat")
    suspend fun getChat(
        @Path("token") token: String,
        @Query("chat_id") chatId: Long,
    ): Response<ResponseDto<ChatFullInfoDto>>

    // https://core.telegram.org/bots/api#getupdates
    @GET("/bot{token}/getUpdates")
    suspend fun getUpdates(
        @Path("token") token: String,
        @Query("timeout") timeout: Long? = null,
        @Query("offset") offset: Long? = null,
        @Query("allowed_updates") allowedUpdates: List<String>? = null,
    ): Response<ResponseDto<List<UpdateDto>>>

    // https://core.telegram.org/bots/api#sendmessage
    @FormUrlEncoded
    @POST("/bot{token}/sendMessage")
    suspend fun sendMessage(
        @Path("token") token: String,
        @Field("chat_id") chatId: Long,
        @Field("text") text: String,
    ): Response<ResponseDto<Unit>>
}
