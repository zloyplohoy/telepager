package ag.sokolov.telepager.core.database.entity

import ag.sokolov.telepager.core.model.BotDetails
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "bot_details",
    foreignKeys = [
        ForeignKey(
            entity = BotTokenEntity::class,
            parentColumns = ["record_id"],
            childColumns = ["bot_token_record_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class BotDetailsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "record_id")
    val recordId: Int = 1,
    @ColumnInfo(name = "bot_token_record_id")
    val botTokenRecordId: Int = 1,
    val id: Long,
    val name: String,
    val username: String,
)

fun BotDetailsEntity.asExternalModel() = BotDetails(
    id = id,
    name = name,
    username = username,
)
