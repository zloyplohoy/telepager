package ag.sokolov.telepager.core.telegram.retrofit.dto

import kotlinx.serialization.Serializable

// https://core.telegram.org/bots/api#making-requests

@Serializable
data class ResponseDto<T>(
    val ok: Boolean,
    val result: T,
)
