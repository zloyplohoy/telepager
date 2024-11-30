package ag.sokolov.telepager.core.telegram.retrofit

import ag.sokolov.telepager.core.telegram.retrofit.dto.MessageDto
import ag.sokolov.telepager.core.telegram.retrofit.dto.ResponseDto
import ag.sokolov.telepager.core.telegram.retrofit.dto.UpdateDto
import ag.sokolov.telepager.core.telegram.retrofit.dto.UserDto
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
    ): ResponseDto<UserDto>

    // https://core.telegram.org/bots/api#getchat
    @GET("/bot{token}/getChat")
    suspend fun getChat(
        @Path("token") token: String,
        @Query("chat_id") chatId: Long,
    ): ResponseDto<UserDto>

    // https://core.telegram.org/bots/api#getupdates
    @GET("/bot{token}/getUpdates")
    suspend fun getUpdates(
        @Path("token") token: String,
        @Query("timeout") timeout: Long?,
        @Query("offset") offset: Long?,
        @Query("allowed_updates") allowedUpdates: List<String>?,
    ): ResponseDto<List<UpdateDto>>

    // https://core.telegram.org/bots/api#sendmessage
    @FormUrlEncoded
    @POST("/bot{token}/sendMessage")
    suspend fun sendMessage(
        @Path("token") token: String,
        @Field("chat_id") chatId: Long,
        @Field("text") text: String,
    ): ResponseDto<MessageDto>
}
