package ag.sokolov.telepager.core.domain.recipientregistration;

import kotlinx.coroutines.flow.Flow

interface RecipientRegistrationManager {
    fun registerRecipient(): Flow<RecipientRegistrationState>
}
