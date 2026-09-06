package com.openwhispr.android.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.openwhispr.android.core.database.entity.NoteEntity
import com.openwhispr.android.core.database.entity.NoteFtsEntity
import com.openwhispr.android.core.database.entity.TranscriptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE deleted_at IS NULL ORDER BY updated_at DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    fun getNoteById(id: Long): Flow<NoteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)
    
    @Query("""
        SELECT notes.* FROM notes 
        JOIN notes_fts ON notes.id = notes_fts.rowid 
        WHERE notes_fts MATCH :query AND notes.deleted_at IS NULL
    """)
    fun searchNotes(query: String): Flow<List<NoteEntity>>
}

@Dao
interface TranscriptionDao {
    @Query("SELECT * FROM transcriptions WHERE deleted_at IS NULL ORDER BY timestamp DESC")
    fun getAllTranscriptions(): Flow<List<TranscriptionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranscription(transcription: TranscriptionEntity): Long

    @Update
    suspend fun updateTranscription(transcription: TranscriptionEntity)
}
