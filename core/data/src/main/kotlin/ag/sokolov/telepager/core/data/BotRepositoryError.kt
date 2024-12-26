package ag.sokolov.telepager.core.data

sealed class BotRepositoryError {
    data object InvalidToken: BotRepositoryError()
    data object NetworkError: BotRepositoryError()
    data object UnknownError: BotRepositoryError()
}
