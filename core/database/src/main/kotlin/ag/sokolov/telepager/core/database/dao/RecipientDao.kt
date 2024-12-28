package ag.sokolov.telepager.core.database.dao

import ag.sokolov.telepager.core.database.entity.RecipientEntity
import androidx.room.Dao
import androidx.room.Query
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

    @Query(
        """
        INSERT OR REPLACE
        INTO recipient (id, first_name, last_name, username)
        VALUES (:id, :firstName, :lastName, :username)
        """
    )
    fun addRecipient(id: Long, firstName: String, lastName: String?, username: String?)

    @Query(
        """
        UPDATE recipient
        SET first_name = :firstName, last_name = :lastName, username = :username
        WHERE id = :id
        """
    )
    fun setDetails(id: Long, firstName: String, lastName: String?, username: String?)

    @Query(
        """
        UPDATE recipient
        SET is_deleted = :isDeleted
        WHERE id = :id
        """
    )
    fun setIsDeleted(id: Long, isDeleted: Boolean)

    @Query(
        """
        UPDATE recipient
        SET is_bot_blocked = :isBotBlocked
        WHERE id = :id
        """
    )
    fun setIsBotBlocked(id: Long, isBotBlocked: Boolean)

    @Query(
        """
        DELETE
        FROM recipient
        WHERE id = :id
        """
    )
    fun deleteRecipient(id: Long)
}
