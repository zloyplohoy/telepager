package ag.sokolov.telepager.core.telegram.retrofit.dto

import kotlinx.serialization.Serializable

// https://core.telegram.org/bots/api#message

@Serializable
data class MessageDto(
    val from: UserDto?,
    val text: String?,
)
