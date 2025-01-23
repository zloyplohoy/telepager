package ag.sokolov.telepager.core.database.dao

import ag.sokolov.telepager.core.database.entity.BotTokenEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BotTokenDao {
    @Query(
        """
        SELECT * FROM bot_token
        WHERE record_id = 0
        """
    )
    fun getBotToken(): Flow<BotTokenEntity?>

    @Insert(onConflict = REPLACE)
    fun insertBotToken(botTokenEntity: BotTokenEntity)

    @Query(
        """
        UPDATE bot_token
        SET is_valid = :isValid
        WHERE record_id = 0
        """
    )
    fun updateIsBotTokenValid(isValid: Boolean)

    @Query(
        """
        DELETE FROM bot_token
        WHERE record_id = 0
        """
    )
    fun deleteBotToken()
}
