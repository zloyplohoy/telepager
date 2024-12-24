package ag.sokolov.telepager.core.result

sealed class Result<out T, out E> {
    data class Success<out T>(val data: T? = null) : Result<T, Nothing>()
    data class Failure<out E>(val error: E) : Result<Nothing, E>()
}
