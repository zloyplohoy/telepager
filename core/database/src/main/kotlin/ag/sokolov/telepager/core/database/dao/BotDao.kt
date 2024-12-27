package ag.sokolov.telepager.core.database.dao

import ag.sokolov.telepager.core.database.entity.BotEntity
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BotDao {
    @Query(
        """
        SELECT *
        FROM bot
        LIMIT 1
        """
    )
    fun getBot(): Flow<BotEntity?>

    @Query(
        """
        SELECT token
        FROM bot
        LIMIT 1
        """
    )
    fun getBotToken(): Flow<String?>

    @Query(
        """
        INSERT
        INTO bot (record_id, token, id, name, username)
        VALUES (1, :token, :id, :name, :username)
        """
    )
    fun setBot(token: String, id: Long, name: String, username: String)

    @Query(
        """
        UPDATE bot
        SET is_token_valid = :isTokenValid
        WHERE record_id = 1
        """
    )
    suspend fun setIsTokenValid(isTokenValid: Boolean)

    @Query(
        """
        UPDATE bot
        SET name = :name, username = :username
        WHERE record_id = 1
        """
    )
    suspend fun setDetails(name: String, username: String)

    @Query(
        """
        DELETE
        FROM bot
        WHERE record_id = 1
        """
    )
    suspend fun deleteBot()
}
