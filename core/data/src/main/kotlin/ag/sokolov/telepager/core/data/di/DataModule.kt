package ag.sokolov.telepager.core.data.di

import ag.sokolov.telepager.core.data.BotRepository
import ag.sokolov.telepager.core.data.BotRepositoryImpl
import ag.sokolov.telepager.core.data.RecipientRepository
import ag.sokolov.telepager.core.data.RecipientRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindBotRepository(impl: BotRepositoryImpl): BotRepository

    @Binds
    internal abstract fun bindRecipientRepository(impl: RecipientRepositoryImpl): RecipientRepository
}
