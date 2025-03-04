package ag.sokolov.telepager.core.telegram

import ag.sokolov.telepager.core.telegram.TelegramBotApiError.BadRequest
import ag.sokolov.telepager.core.telegram.TelegramBotApiError.Forbidden
import ag.sokolov.telepager.core.telegram.TelegramBotApiError.Unauthorized
import ag.sokolov.telepager.core.telegram.TelegramBotApiError.UnknownError

sealed class TelegramBotApiError {
    // Generic errors
    data class NetworkError(val message: String?) : TelegramBotApiError()
    data class UnknownError(val message: String?) : TelegramBotApiError()

    // Bot API errors
    sealed class BadRequest : TelegramBotApiError() {
        data object ChatNotFound : BadRequest()
        data object MessageTextIsEmpty : BadRequest()
    }

    sealed class Forbidden : TelegramBotApiError() {
        data object BotWasBlockedByTheUser : Forbidden()
        data object UserIsDeactivated : Forbidden()
    }

    data object Unauthorized : TelegramBotApiError()
}

internal fun mapTelegramBotApiError(
    statusCode: Int,
    errorDescription: String?,
): TelegramBotApiError {
    return when (statusCode) {
        400 -> when (errorDescription) {
            "Bad Request: chat not found" -> BadRequest.ChatNotFound
            "Bad Request: message text is empty" -> BadRequest.MessageTextIsEmpty
            else -> UnknownError(errorDescription)
        }

        401 -> Unauthorized
        403 -> when (errorDescription) {
            "Forbidden: bot was blocked by the user" -> Forbidden.BotWasBlockedByTheUser
            "Forbidden: user is deactivated" -> Forbidden.UserIsDeactivated
            else -> UnknownError(errorDescription)
        }

        else -> UnknownError(errorDescription)
    }
}
