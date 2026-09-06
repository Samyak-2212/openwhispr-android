package com.openwhispr.android.core.whisper

interface WhisperEngine {
    suspend fun loadModel(modelPath: String): Boolean
    suspend fun transcribe(audioPath: String, config: TranscriptionConfig): TranscriptionResult
    fun isModelLoaded(): Boolean
    fun unloadModel()
}
