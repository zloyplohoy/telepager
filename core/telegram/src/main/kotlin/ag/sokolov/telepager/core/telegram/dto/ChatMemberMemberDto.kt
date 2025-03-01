package ag.sokolov.telepager.core.telegram.dto

import kotlinx.serialization.Serializable

// https://core.telegram.org/bots/api#chatmembermember

@Serializable
data class ChatMemberMemberDto(
    val status: String,
    val user: UserDto,
)
