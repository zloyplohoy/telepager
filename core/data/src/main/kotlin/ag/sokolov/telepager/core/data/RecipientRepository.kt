package ag.sokolov.telepager.core.data

import ag.sokolov.telepager.core.model.Recipient
import ag.sokolov.telepager.core.result.Result
import kotlinx.coroutines.flow.Flow

interface RecipientRepository {
    fun getRecipients(): Flow<List<Recipient>>
    fun getRecipientIds(): Flow<List<Long>>
    suspend fun addRecipient(
        id: Long,
        firstName: String,
        lastName: String?,
        username: String?,
    ): Result<Nothing, RepositoryError>

    suspend fun updateDetails(): Result<Nothing, RepositoryError>

    suspend fun deleteRecipient(id: Long): Result<Nothing, RepositoryError>
}
