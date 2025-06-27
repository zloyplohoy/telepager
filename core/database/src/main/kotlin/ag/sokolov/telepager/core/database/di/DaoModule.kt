package ag.sokolov.telepager.core.database.di

import ag.sokolov.telepager.core.database.TelepagerDatabase
import org.koin.dsl.module

internal val daoModule = module {
    single { get<TelepagerDatabase>().botDao() }
    single { get<TelepagerDatabase>().botTokenDao() }
    single { get<TelepagerDatabase>().recipientDao() }
}
