package ag.sokolov.telepager.core.database.dao

import ag.sokolov.telepager.core.database.entity.BotDetailsEntity
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BotDetailsDao {
    @Query(
        """
        INSERT OR REPLACE
        INTO bot_details (record_id, bot_token_record_id, id, name, username)
        VALUES (1, 1, :id, :name, :username)
        """
    )
    suspend fun setBotDetails(id: Long, name: String, username: String)

    @Query(
        """
        SELECT *
        FROM bot_details
        LIMIT 1
        """
    )
    fun getBotDetails(): Flow<BotDetailsEntity?>
}
