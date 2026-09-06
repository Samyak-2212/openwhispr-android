package com.openwhispr.android.core.audio

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioPlaybackCaptureConfiguration
import android.media.AudioRecord
import android.media.projection.MediaProjection
import android.os.Build
import androidx.annotation.RequiresApi
import com.openwhispr.android.core.audio.model.AudioConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Handles internal system audio capture (API 29+).
 */
class MediaProjectionCapture @Inject constructor() {

    fun isSupported(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("MissingPermission")
    fun startCapture(mediaProjection: MediaProjection, config: AudioConfig): Flow<ShortArray> = flow {
        if (!isSupported()) throw UnsupportedOperationException("AudioPlaybackCapture requires API 29+")

        val captureConfig = AudioPlaybackCaptureConfiguration.Builder(mediaProjection)
            .addMatchingUsage(android.media.AudioAttributes.USAGE_MEDIA)
            .addMatchingUsage(android.media.AudioAttributes.USAGE_GAME)
            .addMatchingUsage(android.media.AudioAttributes.USAGE_UNKNOWN)
            .build()

        val minBufferSize = AudioRecord.getMinBufferSize(
            config.sampleRate,
            config.channelConfig,
            config.audioFormat
        )
        val bufferSize = minBufferSize * 2

        val audioRecord = AudioRecord.Builder()
            .setAudioFormat(
                AudioFormat.Builder()
                    .setEncoding(config.audioFormat)
                    .setSampleRate(config.sampleRate)
                    .setChannelMask(config.channelConfig)
                    .build()
            )
            .setBufferSizeInBytes(bufferSize)
            .setAudioPlaybackCaptureConfig(captureConfig)
            .build()

        if (audioRecord.state != AudioRecord.STATE_INITIALIZED) {
            throw IllegalStateException("AudioRecord initialization failed for MediaProjection")
        }

        audioRecord.startRecording()
        try {
            val buffer = ShortArray(bufferSize / 2)
            while (true) {
                val readResult = audioRecord.read(buffer, 0, buffer.size)
                if (readResult > 0) {
                    emit(buffer.copyOf(readResult))
                } else if (readResult < 0) {
                    throw IllegalStateException("AudioRecord read error: $readResult")
                }
            }
        } finally {
            audioRecord.stop()
            audioRecord.release()
        }
    }.flowOn(Dispatchers.IO)
}
