package ag.sokolov.telepager.core.database

import ag.sokolov.telepager.core.database.dao.BotDao
import ag.sokolov.telepager.core.database.dao.BotTokenDao
import ag.sokolov.telepager.core.database.dao.RecipientDao
import ag.sokolov.telepager.core.database.entity.BotEntity
import ag.sokolov.telepager.core.database.entity.BotTokenEntity
import ag.sokolov.telepager.core.database.entity.RecipientEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        BotTokenEntity::class,
        BotEntity::class,
        RecipientEntity::class
    ],
    version = 1,
    exportSchema = true
)
internal abstract class TelepagerDatabase : RoomDatabase() {
    abstract fun botTokenDao(): BotTokenDao
    abstract fun botDao(): BotDao
    abstract fun recipientDao(): RecipientDao
}
