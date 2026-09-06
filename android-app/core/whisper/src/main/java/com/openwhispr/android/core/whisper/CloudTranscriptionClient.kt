package com.openwhispr.android.core.whisper

import javax.inject.Inject

class CloudTranscriptionClient @Inject constructor() {
    suspend fun transcribe(audioPath: String, config: TranscriptionConfig): TranscriptionResult {
        // Mock fallback to cloud APIs
        return TranscriptionResult(
            text = "Cloud transcription fallback",
            languageDetected = config.language,
            durationMs = 0
        )
    }
}
