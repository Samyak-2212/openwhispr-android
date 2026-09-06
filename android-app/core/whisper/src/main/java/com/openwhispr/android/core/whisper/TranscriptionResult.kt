package com.openwhispr.android.core.whisper

data class TranscriptionResult(
    val text: String,
    val languageDetected: String,
    val durationMs: Long,
    val segments: List<TranscriptionSegment> = emptyList()
)

data class TranscriptionSegment(
    val startMs: Long,
    val endMs: Long,
    val text: String
)
