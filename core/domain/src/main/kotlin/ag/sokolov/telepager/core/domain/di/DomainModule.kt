package ag.sokolov.telepager.core.domain.di

import ag.sokolov.telepager.core.domain.recipientregistration.RecipientRegistrationManager
import ag.sokolov.telepager.core.domain.recipientregistration.RecipientRegistrationManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @Binds
    internal abstract fun bindRecipientRegistrationManager(impl: RecipientRegistrationManagerImpl): RecipientRegistrationManager
}
