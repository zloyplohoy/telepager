package ag.sokolov.telepager.core.data

sealed class RecipientRepositoryError {
    data object InvalidToken: RecipientRepositoryError()
    data object NetworkError: RecipientRepositoryError()
    data object UnknownError: RecipientRepositoryError()
}
