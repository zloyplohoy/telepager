package ag.sokolov.telepager.core.database.di

import ag.sokolov.telepager.core.database.TelepagerDatabase
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun provideTelepagerDatabase(
        @ApplicationContext context: Context,
    ): TelepagerDatabase = Room.databaseBuilder(
        context,
        TelepagerDatabase::class.java,
        "telepager-database"
    ).build()
}
