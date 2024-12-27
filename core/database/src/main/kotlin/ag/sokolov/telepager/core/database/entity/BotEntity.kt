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
    @ColumnInfo(name = "record_id")
    val recordId: Int = 1,
    val token: String,
    @ColumnInfo(name = "is_valid")
    val isValid: Boolean,
    val id: Long,
    val name: String,
    val username: String,
)

fun BotEntity.asExternalModel() = Bot(
    isValid = isValid,
    name = name,
    username = username,
)
