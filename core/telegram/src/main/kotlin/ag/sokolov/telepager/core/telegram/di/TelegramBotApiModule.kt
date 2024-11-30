package ag.sokolov.telepager.core.telegram.di

import ag.sokolov.telepager.core.telegram.TelegramBotApi
import ag.sokolov.telepager.core.telegram.TelegramBotApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TelegramBotApiModule {
    @Binds
    internal abstract fun bindTelegramBotApi(impl: TelegramBotApiImpl): TelegramBotApi
}
