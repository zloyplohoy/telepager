package ag.sokolov.telepager.core.telegram

sealed class TelegramBotApiError {
    // Generic errors
    data class NetworkError(val message: String?) : TelegramBotApiError()
    data class UnknownError(val message: String?) : TelegramBotApiError()

    // Bot API errors
    data object Unauthorized : TelegramBotApiError()

    sealed class Forbidden : TelegramBotApiError() {
        data object BotWasBlockedByTheUser : Forbidden()
    }

    sealed class BadRequest : TelegramBotApiError() {
        data object MessageTextIsEmpty : BadRequest()
        data object ChatNotFound : BadRequest()
    }
}
