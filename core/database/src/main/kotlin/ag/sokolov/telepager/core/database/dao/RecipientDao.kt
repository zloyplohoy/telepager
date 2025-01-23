package ag.sokolov.telepager.core.database.dao

import ag.sokolov.telepager.core.database.entity.RecipientEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipientDao {
    @Query(
        """
        SELECT *
        FROM recipient
        """
    )
    fun getRecipients(): Flow<List<RecipientEntity>>

    @Insert(onConflict = REPLACE)
    fun insertRecipient(recipientEntity: RecipientEntity)

    @Update
    fun updateRecipient(recipientEntity: RecipientEntity)

    @Query(
        """
        DELETE
        FROM recipient
        WHERE id = :id
        """
    )
    fun deleteRecipient(id: Long)
}
