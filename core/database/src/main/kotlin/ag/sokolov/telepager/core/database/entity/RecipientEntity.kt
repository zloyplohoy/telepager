package ag.sokolov.telepager.core.database.entity

import ag.sokolov.telepager.core.model.Recipient
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipient"
)
data class RecipientEntity (
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String? = null,
    val username: String? = null,
    @ColumnInfo(name = "is_bot_blocked")
    val isBotBlocked: Boolean = false,
)

fun RecipientEntity.asExternalModel() = Recipient(
    id = id,
    firstName = firstName,
    lastName = lastName,
    username = username,
    isBotBlocked = isBotBlocked
)
