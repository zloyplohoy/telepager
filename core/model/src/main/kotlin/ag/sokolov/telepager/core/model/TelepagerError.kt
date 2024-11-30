package ag.sokolov.telepager.core.model

sealed class TelepagerError {
    data object NetworkError : TelepagerError()
    data object BotApiTokenInvalid : TelepagerError()
    data object UnhandledError : TelepagerError()
}
