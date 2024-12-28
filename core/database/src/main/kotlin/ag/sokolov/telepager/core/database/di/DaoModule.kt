package ag.sokolov.telepager.core.database.di

import ag.sokolov.telepager.core.database.TelepagerDatabase
import ag.sokolov.telepager.core.database.dao.BotDao
import ag.sokolov.telepager.core.database.dao.RecipientDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    fun provideBotDao(
        database: TelepagerDatabase,
    ): BotDao = database.botDao()

    @Provides
    fun provideRecipientDao(
        database: TelepagerDatabase,
    ): RecipientDao = database.recipientDao()
}
