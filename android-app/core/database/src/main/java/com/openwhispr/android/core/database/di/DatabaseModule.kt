package com.openwhispr.android.core.database.di

import android.content.Context
import androidx.room.Room
import com.openwhispr.android.core.database.OpenWhisprDatabase
import com.openwhispr.android.core.database.dao.NoteDao
import com.openwhispr.android.core.database.dao.TranscriptionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideOpenWhisprDatabase(
        @ApplicationContext context: Context
    ): OpenWhisprDatabase {
        return Room.databaseBuilder(
            context,
            OpenWhisprDatabase::class.java,
            "openwhispr_database"
        ).build()
    }

    @Provides
    fun provideNoteDao(database: OpenWhisprDatabase): NoteDao {
        return database.noteDao()
    }

    @Provides
    fun provideTranscriptionDao(database: OpenWhisprDatabase): TranscriptionDao {
        return database.transcriptionDao()
    }
}
