package ag.sokolov.telepager.core.concurrency.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val coroutineScopesModule = module {
    single(named("ApplicationScope")) {
        val defaultDispatcher: CoroutineDispatcher = get(named("Default"))
        CoroutineScope(SupervisorJob() + defaultDispatcher)
    }
}
