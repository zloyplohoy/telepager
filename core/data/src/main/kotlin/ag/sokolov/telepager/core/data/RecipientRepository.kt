package ag.sokolov.telepager.core.data

import ag.sokolov.telepager.core.model.Recipient
import ag.sokolov.telepager.core.model.RecipientState
import kotlinx.coroutines.flow.Flow

interface RecipientRepository {
    fun getRecipientList(): Flow<List<Recipient>>
    suspend fun addRecipient(recipient: Recipient)
    suspend fun updateRecipient(recipient: Recipient)
    suspend fun deleteRecipient(id: Long)

    fun getRecipientIdList(): Flow<List<Long>>
    fun getRecipientStateList(): Flow<List<RecipientState>>
}
