package ag.sokolov.telepager.core.database.entity

import ag.sokolov.telepager.core.model.BotToken
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "bot_token"
)
data class BotTokenEntity(
    @PrimaryKey
    @ColumnInfo(name = "record_id")
    val recordId: Long = 0,
    val token: String,
    @ColumnInfo(name = "is_valid")
    val isValid: Boolean = true,
)

fun BotTokenEntity.asExternalModel() = BotToken(
    token = token,
    isValid = isValid,
)
