package ag.sokolov.telepager.core.database.di

import ag.sokolov.telepager.core.database.TelepagerDatabase
import ag.sokolov.telepager.core.database.dao.BotTokenDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    fun provideBotTokenDao(
        database: TelepagerDatabase,
    ): BotTokenDao = database.botTokenDao()
}
