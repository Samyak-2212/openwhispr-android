package com.openwhispr.android.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Transcription entity.
 */
@Entity(tableName = "transcriptions")
data class TranscriptionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "raw_text") val rawText: String? = null,
    @ColumnInfo(name = "timestamp") val timestamp: Date? = null,
    @ColumnInfo(name = "created_at") val createdAt: Date? = null,
    @ColumnInfo(name = "has_audio") val hasAudio: Int = 0,
    @ColumnInfo(name = "audio_duration_ms") val audioDurationMs: Long? = null,
    @ColumnInfo(name = "provider") val provider: String? = null,
    @ColumnInfo(name = "model") val model: String? = null,
    @ColumnInfo(name = "status") val status: String = "completed",
    @ColumnInfo(name = "error_message") val errorMessage: String? = null,
    @ColumnInfo(name = "error_code") val errorCode: String? = null,
    @ColumnInfo(name = "route_kind") val routeKind: String? = null,
    @ColumnInfo(name = "client_transcription_id") val clientTranscriptionId: String? = null,
    @ColumnInfo(name = "cloud_id") val cloudId: String? = null,
    @ColumnInfo(name = "sync_status") val syncStatus: String? = "pending",
    @ColumnInfo(name = "deleted_at") val deletedAt: String? = null
)
