package com.openwhispr.android.core.ai.model

enum class InferenceScope {
    DICTATION_CLEANUP,
    DICTATION_AGENT,
    NOTE_FORMATTING,
    CHAT_INTELLIGENCE
}

data class ScopeConfig(
    val provider: String,
    val model: String
)
