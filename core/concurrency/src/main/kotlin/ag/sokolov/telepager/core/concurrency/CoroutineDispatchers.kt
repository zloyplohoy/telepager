package ag.sokolov.telepager.core.concurrency

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val coroutineDispatcher: CoroutineDispatchers)

enum class CoroutineDispatchers {
    Default,
    IO
}
