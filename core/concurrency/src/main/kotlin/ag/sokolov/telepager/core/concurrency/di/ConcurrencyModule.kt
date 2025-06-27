package ag.sokolov.telepager.core.concurrency.di

import org.koin.dsl.module

val concurrencyModule = module {
    includes(
        coroutineScopesModule,
        dispatchersModule
    )
}
