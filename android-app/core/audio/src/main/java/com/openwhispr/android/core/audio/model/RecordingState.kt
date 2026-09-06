package com.openwhispr.android.core.audio.model

/**
 * State of the audio recording.
 */
enum class RecordingState {
    IDLE,
    STARTING,
    RECORDING,
    PAUSED,
    STOPPING,
    ERROR
}
