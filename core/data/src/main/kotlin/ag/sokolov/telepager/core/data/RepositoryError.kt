package ag.sokolov.telepager.core.data

import ag.sokolov.telepager.core.result.Result
import ag.sokolov.telepager.core.result.Result.Failure
import ag.sokolov.telepager.core.result.Result.Success
import ag.sokolov.telepager.core.telegram.TelegramBotApiError

sealed class RepositoryError {
    data object TokenNotSet : RepositoryError()
    data object InvalidToken : RepositoryError()
    data object NetworkError : RepositoryError()
    data object UnknownError : RepositoryError()
    data object RecipientRegistrationTimeoutExceeded : RepositoryError()
}

fun Result<Nothing, TelegramBotApiError>.asRepositoryResult(): Result<Nothing, RepositoryError> =
    when (this) {
        is Success -> Success()
        is Failure -> when (this.error) {
            is TelegramBotApiError.Unauthorized -> Failure(RepositoryError.InvalidToken)
            is TelegramBotApiError.NetworkError -> Failure(RepositoryError.NetworkError)
            else -> Failure(RepositoryError.UnknownError)
        }
    }
