package ag.sokolov.telepager.core.telegram.di

import ag.sokolov.telepager.core.concurrency.di.concurrencyModule
import ag.sokolov.telepager.core.telegram.TelegramBotApi
import ag.sokolov.telepager.core.telegram.ktor.KtorTelegramBotApi
import org.koin.dsl.module

val telegramModule = module {
    single<TelegramBotApi> { KtorTelegramBotApi() }
    includes(
        concurrencyModule,
        jsonModule
    )
}
