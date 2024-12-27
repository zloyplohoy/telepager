package ag.sokolov.telepager.core.database

import ag.sokolov.telepager.core.database.dao.BotDao
import ag.sokolov.telepager.core.database.entity.BotEntity
import ag.sokolov.telepager.core.database.entity.RecipientEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        BotEntity::class,
        RecipientEntity::class
    ],
    version = 1,
    exportSchema = true
)
internal abstract class TelepagerDatabase : RoomDatabase() {
    abstract fun botDao(): BotDao
}
