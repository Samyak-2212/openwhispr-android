package com.openwhispr.android.core.audio.model

/**
 * Represents the current audio level for UI metering.
 */
data class AudioLevel(
    val rms: Float,
    val peak: Float
)
