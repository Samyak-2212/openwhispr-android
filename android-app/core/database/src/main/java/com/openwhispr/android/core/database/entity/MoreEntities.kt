package com.openwhispr.android.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import java.util.Date

@Fts4(contentEntity = NoteEntity::class)
@Entity(tableName = "notes_fts")
data class NoteFtsEntity(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "enhanced_content") val enhancedContent: String?
)

@Entity(tableName = "custom_dictionary")
data class CustomDictionaryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "word") val word: String,
    @ColumnInfo(name = "created_at") val createdAt: Date? = null,
    @ColumnInfo(name = "client_dict_id") val clientDictId: String? = null,
    @ColumnInfo(name = "cloud_id") val cloudId: String? = null,
    @ColumnInfo(name = "source") val source: String = "manual",
    @ColumnInfo(name = "sync_status") val syncStatus: String? = "pending",
    @ColumnInfo(name = "deleted_at") val deletedAt: String? = null,
    @ColumnInfo(name = "updated_at") val updatedAt: Date? = null
)

@Entity(tableName = "calendar_events")
data class CalendarEventEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "calendar_id") val calendarId: String,
    @ColumnInfo(name = "summary") val summary: String?,
    @ColumnInfo(name = "start_time") val startTime: String,
    @ColumnInfo(name = "end_time") val endTime: String,
    @ColumnInfo(name = "is_all_day") val isAllDay: Int = 0,
    @ColumnInfo(name = "status") val status: String = "confirmed",
    @ColumnInfo(name = "availability_status") val availabilityStatus: String = "unknown",
    @ColumnInfo(name = "self_response_status") val selfResponseStatus: String = "unknown",
    @ColumnInfo(name = "hangout_link") val hangoutLink: String?,
    @ColumnInfo(name = "conference_data") val conferenceData: String?,
    @ColumnInfo(name = "organizer_email") val organizerEmail: String?,
    @ColumnInfo(name = "attendees_count") val attendeesCount: Int? = 0,
    @ColumnInfo(name = "synced_at") val syncedAt: Date? = null,
    @ColumnInfo(name = "provider") val provider: String = "google",
    @ColumnInfo(name = "attendees") val attendees: String?
)

@Entity(tableName = "speaker_profiles")
data class SpeakerProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "display_name") val displayName: String,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "embedding") val embedding: ByteArray,
    @ColumnInfo(name = "sample_count") val sampleCount: Int? = 1,
    @ColumnInfo(name = "created_at") val createdAt: Date? = null,
    @ColumnInfo(name = "updated_at") val updatedAt: Date? = null
)

@Entity(
    tableName = "speaker_mappings",
    primaryKeys = ["note_id", "speaker_id"]
)
data class SpeakerMappingEntity(
    @ColumnInfo(name = "note_id") val noteId: Long,
    @ColumnInfo(name = "speaker_id") val speakerId: String,
    @ColumnInfo(name = "profile_id") val profileId: Long?,
    @ColumnInfo(name = "display_name") val displayName: String
)

@Entity(
    tableName = "note_speaker_embeddings",
    primaryKeys = ["note_id", "speaker_id"]
)
data class NoteSpeakerEmbeddingEntity(
    @ColumnInfo(name = "note_id") val noteId: Long,
    @ColumnInfo(name = "speaker_id") val speakerId: String,
    @ColumnInfo(name = "embedding") val embedding: ByteArray
)

@Entity(tableName = "snippets")
data class SnippetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "trigger") val trigger: String,
    @ColumnInfo(name = "replacement") val replacement: String,
    @ColumnInfo(name = "client_snippet_id") val clientSnippetId: String?,
    @ColumnInfo(name = "cloud_id") val cloudId: String?,
    @ColumnInfo(name = "sync_status") val syncStatus: String? = "pending",
    @ColumnInfo(name = "deleted_at") val deletedAt: String?,
    @ColumnInfo(name = "created_at") val createdAt: Date? = null,
    @ColumnInfo(name = "updated_at") val updatedAt: Date? = null
)
