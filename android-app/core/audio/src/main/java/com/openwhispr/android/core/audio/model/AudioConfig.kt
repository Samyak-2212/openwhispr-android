package com.openwhispr.android.core.audio.model

import android.media.AudioFormat

/**
 * Configuration for audio recording.
 * Matches whisper.cpp expectations by default: 16kHz, mono, 16-bit PCM.
 */
data class AudioConfig(
    val sampleRate: Int = 16000,
    val channelConfig: Int = AudioFormat.CHANNEL_IN_MONO,
    val audioFormat: Int = AudioFormat.ENCODING_PCM_16BIT
)
