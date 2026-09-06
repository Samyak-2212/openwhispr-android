package com.openwhispr.android.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.openwhispr.android.core.database.converter.DateConverter
import com.openwhispr.android.core.database.dao.NoteDao
import com.openwhispr.android.core.database.dao.TranscriptionDao
import com.openwhispr.android.core.database.entity.CalendarEventEntity
import com.openwhispr.android.core.database.entity.CustomDictionaryEntity
import com.openwhispr.android.core.database.entity.FolderEntity
import com.openwhispr.android.core.database.entity.NoteEntity
import com.openwhispr.android.core.database.entity.NoteFtsEntity
import com.openwhispr.android.core.database.entity.NoteSpeakerEmbeddingEntity
import com.openwhispr.android.core.database.entity.SnippetEntity
import com.openwhispr.android.core.database.entity.SpeakerMappingEntity
import com.openwhispr.android.core.database.entity.SpeakerProfileEntity
import com.openwhispr.android.core.database.entity.TranscriptionEntity

@Database(
    entities = [
        NoteEntity::class,
        NoteFtsEntity::class,
        FolderEntity::class,
        TranscriptionEntity::class,
        CustomDictionaryEntity::class,
        CalendarEventEntity::class,
        SpeakerProfileEntity::class,
        SpeakerMappingEntity::class,
        NoteSpeakerEmbeddingEntity::class,
        SnippetEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(DateConverter::class)
abstract class OpenWhisprDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun transcriptionDao(): TranscriptionDao
}
