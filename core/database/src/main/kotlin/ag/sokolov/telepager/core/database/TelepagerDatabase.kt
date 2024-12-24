package ag.sokolov.telepager.core.database

import ag.sokolov.telepager.core.database.dao.BotDetailsDao
import ag.sokolov.telepager.core.database.dao.BotTokenDao
import ag.sokolov.telepager.core.database.entity.BotDetailsEntity
import ag.sokolov.telepager.core.database.entity.BotTokenEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        BotTokenEntity::class,
        BotDetailsEntity::class
    ],
    version = 1,
    exportSchema = true
)
internal abstract class TelepagerDatabase : RoomDatabase() {
    abstract fun botTokenDao(): BotTokenDao
    abstract fun botDetailsDao(): BotDetailsDao
}
