package ag.sokolov.telepager.core.database.dao

import ag.sokolov.telepager.core.database.entity.BotTokenEntity
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BotTokenDao {
    @Query(
        """
        INSERT OR REPLACE
        INTO bot_token (record_id, value, is_valid)
        VALUES (1, :value, :isValid)
        """
    )
    suspend fun setBotToken(value: String, isValid: Boolean)

    @Query(
        """
        SELECT *
        FROM bot_token
        LIMIT 1
        """
    )
    fun getBotToken(): Flow<BotTokenEntity?>

    @Query(
        """
        SELECT value
        FROM bot_token
        LIMIT 1
        """
    )
    fun getBotTokenValue(): Flow<String?>

    @Query(
        """
        SELECT is_valid
        FROM bot_token
        LIMIT 1
        """
    )
    fun getIsBotTokenValid(): Flow<Boolean?>

    @Query(
        """
        UPDATE bot_token
        SET is_valid = :isValid
        WHERE record_id = 1
        """
    )
    suspend fun setIsBotTokenValid(isValid: Boolean)

    @Query("""
        DELETE
        FROM bot_token
        WHERE record_id = 1
    """)
    suspend fun deleteBotToken()
}
