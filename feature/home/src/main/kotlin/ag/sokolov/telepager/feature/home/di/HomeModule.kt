package ag.sokolov.telepager.feature.home.di

import ag.sokolov.telepager.core.data.di.dataModule
import ag.sokolov.telepager.core.domain.domain.di.domainModule
import ag.sokolov.telepager.feature.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::HomeViewModel)
    includes(
        dataModule,
        domainModule
    )
}
