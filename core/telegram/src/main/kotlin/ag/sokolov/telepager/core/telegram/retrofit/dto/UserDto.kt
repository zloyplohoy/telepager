package ag.sokolov.telepager.core.telegram.retrofit.dto

import ag.sokolov.telepager.core.model.UserDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://core.telegram.org/bots/api#user

@Serializable
data class UserDto(
    val id: Long,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String? = null,
    val username: String? = null,
)

fun UserDto.asUserDetails(): UserDetails =
    UserDetails(
        id = id,
        firstName = firstName,
        lastName = lastName,
        username = username
    )
