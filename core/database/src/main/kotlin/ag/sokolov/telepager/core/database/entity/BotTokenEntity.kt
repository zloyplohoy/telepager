package ag.sokolov.telepager.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "bot_token"
)
data class BotTokenEntity(
    @PrimaryKey
    @ColumnInfo(name = "record_id")
    val recordId: Int = 1,
    val value: String,
    @ColumnInfo(name = "is_valid")
    val isValid: Boolean,
)
