package ag.sokolov.telepager.core.telegram.retrofit.dto

import kotlinx.serialization.Serializable

// https://core.telegram.org/bots/api#making-requests

@Serializable
data class ErrorDto(
    val ok: Boolean,
    val description: String? = null,
)
