package ag.sokolov.telepager.core.telegram.ktor

import io.ktor.resources.Resource
import kotlinx.serialization.SerialName

@Resource("{urlToken}")
internal data class Bot(val urlToken: String) {
    @Resource("getMe")
    data class GetMe(
        val parent: Bot
    )

    @Resource("getChatMember")
    data class GetChatMember(
        val parent: Bot,
        @SerialName("chat_id") val chatId: Long,
        @SerialName("user_id") val userId: Long,
    )

    @Resource("getUpdates")
    data class GetUpdates(
        val parent: Bot,
        val timeout: Long? = null,
        val offset: Long? = null,
        @SerialName("allowed_updates") val allowedUpdates: String? = null,
    )

    @Resource("sendMessage")
    data class SendMessage(
        val parent: Bot,
        @SerialName("chat_id") val chatId: Long,
        val text: String,
    )
}
