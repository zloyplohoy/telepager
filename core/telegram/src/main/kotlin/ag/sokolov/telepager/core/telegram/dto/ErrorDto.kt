package ag.sokolov.telepager.core.telegram.dto

import kotlinx.serialization.Serializable

// https://core.telegram.org/bots/api#making-requests

@Serializable
data class ErrorDto(
    val ok: Boolean,
    val description: String? = null,
)
