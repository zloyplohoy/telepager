package ag.sokolov.telepager.core.telegram.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://core.telegram.org/bots/api#making-requests

@Serializable
data class ResponseDto<T>(
    // All requests
    val ok: Boolean,

    // Successful request
    val result: T? = null,

    // Unsuccessful request
    @SerialName("error_code") val errorCode: Int? = null,
    val description: String? = null,
)
