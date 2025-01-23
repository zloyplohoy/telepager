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
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String?,
    val username: String?,
)

fun RecipientEntity.asExternalModel() = Recipient(
    id = id,
    firstName = firstName,
    lastName = lastName,
    username = username,
)

fun Recipient.asEntity() = RecipientEntity(
    id = id,
    firstName = firstName,
    lastName = lastName,
    username = username,
)
