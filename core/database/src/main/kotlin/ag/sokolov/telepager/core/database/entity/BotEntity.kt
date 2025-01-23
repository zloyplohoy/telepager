package ag.sokolov.telepager.core.database.entity

import ag.sokolov.telepager.core.model.Bot
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "bot",
    foreignKeys = [
        ForeignKey(
            entity = BotTokenEntity::class,
            parentColumns = ["record_id"],
            childColumns = ["record_id"],
            onDelete = CASCADE
        )
    ]
)
data class BotEntity(
    @PrimaryKey
    @ColumnInfo(name = "record_id")
    val recordId: Long = 0,
    val id: Long,
    val name: String,
    val username: String,
)

fun BotEntity.asExternalModel() = Bot(
    id = id,
    name = name,
    username = username,
)

fun Bot.asEntity() = BotEntity(
    id = id,
    name = name,
    username = username,
)
