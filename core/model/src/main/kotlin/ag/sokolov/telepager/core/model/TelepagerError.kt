package ag.sokolov.telepager.core.model

sealed class TelepagerError(open val message: String?) {
    data class NetworkError(override val message: String?) : TelepagerError(message)
    data class BotError(override val message: String?) : TelepagerError(message)
    data class UnknownError(override val message: String?) : TelepagerError(message)
}
