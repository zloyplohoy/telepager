package ag.sokolov.telepager.core.telegram.retrofit.dto

import ag.sokolov.telepager.core.model.Bot
import ag.sokolov.telepager.core.model.Recipient
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

inline fun <reified T> UserDto.asExternalModel(): T =
    when (T::class) {
        Bot::class -> Bot(
            id = id,
            name = firstName,
            username = username!!
        ) as T

        Recipient::class -> Recipient(
            id = id,
            firstName = firstName,
            lastName = lastName,
            username = username
        ) as T

        else -> throw IllegalArgumentException("Unsupported conversion to ${T::class}")
    }
