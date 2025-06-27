package ag.sokolov.telepager.core.data.di

import ag.sokolov.telepager.core.concurrency.di.concurrencyModule
import ag.sokolov.telepager.core.data.BotRepository
import ag.sokolov.telepager.core.data.BotRepositoryImpl
import ag.sokolov.telepager.core.data.RecipientRepository
import ag.sokolov.telepager.core.data.RecipientRepositoryImpl
import ag.sokolov.telepager.core.database.di.databaseModule
import org.koin.dsl.module

val dataModule = module {
    single<BotRepository> { BotRepositoryImpl(get(), get()) }
    single<RecipientRepository> { RecipientRepositoryImpl(get()) }
    includes(
        concurrencyModule,
        databaseModule
    )
}
