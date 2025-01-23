package ag.sokolov.telepager.core.telegram

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
