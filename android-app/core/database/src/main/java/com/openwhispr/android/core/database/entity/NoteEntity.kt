package com.openwhispr.android.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Note entity.
 */
@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "title") val title: String = "Untitled Note",
    @ColumnInfo(name = "content") val content: String = "",
    @ColumnInfo(name = "note_type") val noteType: String = "personal",
    @ColumnInfo(name = "source_file") val sourceFile: String? = null,
    @ColumnInfo(name = "audio_duration_seconds") val audioDurationSeconds: Double? = null,
    @ColumnInfo(name = "created_at") val createdAt: Date? = null,
    @ColumnInfo(name = "updated_at") val updatedAt: Date? = null,
    @ColumnInfo(name = "enhanced_content") val enhancedContent: String? = null,
    @ColumnInfo(name = "enhancement_prompt") val enhancementPrompt: String? = null,
    @ColumnInfo(name = "enhanced_at_content_hash") val enhancedAtContentHash: String? = null,
    @ColumnInfo(name = "cloud_id") val cloudId: String? = null,
    @ColumnInfo(name = "folder_id") val folderId: Long? = null,
    @ColumnInfo(name = "transcript") val transcript: String? = null,
    @ColumnInfo(name = "calendar_event_id") val calendarEventId: String? = null,
    @ColumnInfo(name = "participants") val participants: String? = null,
    @ColumnInfo(name = "diarization_enabled") val diarizationEnabled: Int? = null,
    @ColumnInfo(name = "expected_speaker_count") val expectedSpeakerCount: Int? = null,
    @ColumnInfo(name = "client_note_id") val clientNoteId: String? = null,
    @ColumnInfo(name = "sync_status") val syncStatus: String? = "pending",
    @ColumnInfo(name = "deleted_at") val deletedAt: String? = null,
    @ColumnInfo(name = "is_shared") val isShared: Int = 0,
    @ColumnInfo(name = "share_token") val shareToken: String? = null,
    @ColumnInfo(name = "space_id") val spaceId: Long? = null,
    @ColumnInfo(name = "account_id") val accountId: String? = null,
    @ColumnInfo(name = "left_team") val leftTeam: Int = 0,
    @ColumnInfo(name = "updated_by_user_id") val updatedByUserId: String? = null,
    @ColumnInfo(name = "cloud_updated_at") val cloudUpdatedAt: String? = null,
    @ColumnInfo(name = "owner_user_id") val ownerUserId: String? = null,
    @ColumnInfo(name = "created_by_user_id") val createdByUserId: String? = null
)
