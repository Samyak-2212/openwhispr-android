package com.openwhispr.android.core.whisper

data class TranscriptionConfig(
    val language: String = "en",
    val translate: Boolean = false,
    val maxTokens: Int = 0,
    val prompt: String = "",
    val temperature: Float = 0.0f,
    val beamSize: Int = 5
)
