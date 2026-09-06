package com.openwhispr.android.core.whisper

import android.app.ActivityManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class WhisperEngineImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : WhisperEngine {
    private var contextPtr: Long = 0L

    override suspend fun loadModel(modelPath: String): Boolean = withContext(Dispatchers.IO) {
        val file = File(modelPath)
        if (!file.exists()) return@withContext false
        
        // Memory guard
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val mi = ActivityManager.MemoryInfo()
        am.getMemoryInfo(mi)
        
        if (mi.availMem < file.length()) {
            return@withContext false // OOM prevention
        }
        
        contextPtr = WhisperJni.initContext(modelPath)
        return@withContext contextPtr != 0L
    }

    override suspend fun transcribe(audioPath: String, config: TranscriptionConfig): TranscriptionResult = withContext(Dispatchers.Default) {
        if (contextPtr == 0L) throw IllegalStateException("Model not loaded")
        val audioData = FloatArray(16000)
        val resultString = WhisperJni.transcribe(contextPtr, audioData)
        TranscriptionResult(
            text = resultString,
            languageDetected = config.language,
            durationMs = 1000L
        )
    }

    override fun isModelLoaded(): Boolean = contextPtr != 0L

    override fun unloadModel() {
        if (contextPtr != 0L) {
            WhisperJni.freeContext(contextPtr)
            contextPtr = 0L
        }
    }
}
