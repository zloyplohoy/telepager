package ag.sokolov.telepager.core.telegram.dto

import kotlinx.serialization.Serializable

// https://core.telegram.org/bots/api#making-requests

@Serializable
data class SuccessDto<T>(
    val ok: Boolean,
    val result: T,
)
