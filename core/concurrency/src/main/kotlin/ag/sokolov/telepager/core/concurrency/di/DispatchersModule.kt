package ag.sokolov.telepager.core.concurrency.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val dispatchersModule = module {
    single(named("Default")) { Dispatchers.Default }
    single(named("IO")) { Dispatchers.IO }
}
