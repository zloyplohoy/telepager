package ag.sokolov.telepager.core.database.dao

import ag.sokolov.telepager.core.database.entity.BotEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BotDao {
    @Query(
        """
        SELECT *
        FROM bot
        WHERE record_id = 0
        """
    )
    fun getBot(): Flow<BotEntity?>

    @Insert(onConflict = REPLACE)
    suspend fun insertBot(botEntity: BotEntity)

    @Update
    suspend fun updateBot(botEntity: BotEntity)
}
