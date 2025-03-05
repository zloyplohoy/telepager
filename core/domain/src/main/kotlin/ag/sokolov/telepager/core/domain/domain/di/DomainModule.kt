package ag.sokolov.telepager.core.domain.domain.di

import ag.sokolov.telepager.core.data.di.dataModule
import ag.sokolov.telepager.core.domain.domain.AddBotUseCase
import ag.sokolov.telepager.core.domain.domain.AddRecipientUseCase
import ag.sokolov.telepager.core.domain.domain.UpdateBotUseCase
import ag.sokolov.telepager.core.domain.domain.UpdateRecipientsUseCase
import ag.sokolov.telepager.core.telegram.di.telegramModule
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::AddBotUseCase)
    factoryOf(::AddRecipientUseCase)
    factoryOf(::UpdateBotUseCase)
    factoryOf(::UpdateRecipientsUseCase)
    includes(
        dataModule,
        telegramModule
    )
}
