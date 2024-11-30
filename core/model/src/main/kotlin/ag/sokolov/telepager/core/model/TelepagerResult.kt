package ag.sokolov.telepager.core.model

sealed class TelepagerResult<out T, out E> {
    data class Success<out T>(val data: T? = null) : TelepagerResult<T, Nothing>()
    data class Failure<out E>(val error: E) : TelepagerResult<Nothing, E>()
    data object Loading : TelepagerResult<Nothing, Nothing>()
}
