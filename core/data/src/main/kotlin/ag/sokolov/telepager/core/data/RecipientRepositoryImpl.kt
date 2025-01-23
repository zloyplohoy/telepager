package ag.sokolov.telepager.core.data

import ag.sokolov.telepager.core.concurrency.CoroutineDispatchers.IO
import ag.sokolov.telepager.core.concurrency.Dispatcher
import ag.sokolov.telepager.core.database.dao.RecipientDao
import ag.sokolov.telepager.core.database.entity.asEntity
import ag.sokolov.telepager.core.database.entity.asExternalModel
import ag.sokolov.telepager.core.model.Recipient
import ag.sokolov.telepager.core.model.RecipientState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipientRepositoryImpl @Inject constructor(
    private val recipientDao: RecipientDao,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : RecipientRepository {
    override fun getRecipientList(): Flow<List<Recipient>> =
        recipientDao.getRecipients()
            .distinctUntilChanged()
            .map { it.map { it.asExternalModel() } }

    override suspend fun addRecipient(recipient: Recipient) =
        withContext(ioDispatcher) { recipientDao.insertRecipient(recipient.asEntity()) }

    override suspend fun updateRecipient(recipient: Recipient) =
        withContext(ioDispatcher) { recipientDao.updateRecipient(recipient.asEntity()) }

    override suspend fun deleteRecipient(id: Long) =
        withContext(ioDispatcher) { recipientDao.deleteRecipient(id) }

    override fun getRecipientIdList(): Flow<List<Long>> =
        recipientDao.getRecipients()
            .distinctUntilChanged()
            .map { it.map { it.id } }

    override fun getRecipientStateList(): Flow<List<RecipientState>> =
        recipientDao.getRecipients()
            .map {
                it.map {
                    RecipientState(
                        recipient = it.asExternalModel(),
                        // TODO: implement
                        isBotBlocked = false
                    )
                }
            }
}
