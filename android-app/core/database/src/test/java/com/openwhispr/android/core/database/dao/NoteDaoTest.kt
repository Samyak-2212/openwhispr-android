package com.openwhispr.android.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.openwhispr.android.core.database.OpenWhisprDatabase
import com.openwhispr.android.core.database.entity.NoteEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {

    private lateinit var db: OpenWhisprDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, OpenWhisprDatabase::class.java
        ).allowMainThreadQueries().build()
        noteDao = db.noteDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetNote() = runBlocking {
        val note = NoteEntity(
            title = "Test Note",
            content = "This is a test content.",
            createdAt = Date()
        )
        val id = noteDao.insertNote(note)
        
        val retrieved = noteDao.getNoteById(id).first()
        
        assertNotNull(retrieved)
        assertEquals("Test Note", retrieved?.title)
    }
}
