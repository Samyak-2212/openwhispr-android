package com.openwhispr.android.core.database.repository

import com.openwhispr.android.core.database.dao.NoteDao
import com.openwhispr.android.core.database.dao.TranscriptionDao
import com.openwhispr.android.core.database.entity.NoteEntity
import com.openwhispr.android.core.database.entity.TranscriptionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    fun getAllNotes(): Flow<List<NoteEntity>> = noteDao.getAllNotes()

    fun getNoteById(id: Long): Flow<NoteEntity?> = noteDao.getNoteById(id)

    suspend fun insertNote(note: NoteEntity): Long = noteDao.insertNote(note)

    suspend fun updateNote(note: NoteEntity) = noteDao.updateNote(note)

    suspend fun deleteNote(note: NoteEntity) = noteDao.deleteNote(note)
    
    fun searchNotes(query: String): Flow<List<NoteEntity>> = noteDao.searchNotes(query)
}

class TranscriptionRepository @Inject constructor(
    private val transcriptionDao: TranscriptionDao
) {
    fun getAllTranscriptions(): Flow<List<TranscriptionEntity>> = transcriptionDao.getAllTranscriptions()

    suspend fun insertTranscription(transcription: TranscriptionEntity): Long = 
        transcriptionDao.insertTranscription(transcription)

    suspend fun updateTranscription(transcription: TranscriptionEntity) = 
        transcriptionDao.updateTranscription(transcription)
}
