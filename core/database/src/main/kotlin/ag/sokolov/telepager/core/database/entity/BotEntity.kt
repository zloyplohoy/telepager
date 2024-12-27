package ag.sokolov.telepager.core.database.entity

import ag.sokolov.telepager.core.model.Bot
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "bot"
)
data class BotEntity(
    @PrimaryKey
    @ColumnInfo(name = "record_id", defaultValue = "1")
    val recordId: Int,
    val token: String,
    @ColumnInfo(name = "is_token_valid", defaultValue = "1")
    val isTokenValid: Boolean,
    val id: Long,
    val name: String,
    val username: String,
)

fun BotEntity.asExternalModel() = Bot(
    isTokenValid = isTokenValid,
    name = name,
    username = username,
)
