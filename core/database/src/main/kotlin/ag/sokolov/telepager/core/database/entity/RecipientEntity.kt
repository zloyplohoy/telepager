package ag.sokolov.telepager.core.database.entity

import ag.sokolov.telepager.core.model.Recipient
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipient"
)
data class RecipientEntity(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "is_deleted", defaultValue = "0")
    val isDeleted: Boolean,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String?,
    val username: String?,
    @ColumnInfo(name = "is_bot_blocked", defaultValue = "0")
    val isBotBlocked: Boolean,
)

fun RecipientEntity.asExternalModel() = Recipient(
    id = id,
    isDeleted = isDeleted,
    firstName = firstName,
    lastName = lastName,
    username = username,
    isBotBlocked = isBotBlocked,
)
