package ag.sokolov.telepager.core.telegram.retrofit.dto

import kotlinx.serialization.SerialName

// https://core.telegram.org/bots/api#chatfullinfo

data class ChatFullInfoDto(
    val id: Long,
    @SerialName("first_name") val firstName: String? = null,
    @SerialName("last_name") val lastName: String? = null,
    val username: String? = null,
)
