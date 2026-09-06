package com.openwhispr.android.core.audio

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioRecord
import android.media.MediaRecorder
import com.openwhispr.android.core.audio.model.AudioConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Handles raw audio capture via AudioRecord.
 */
class AudioRecorder @Inject constructor(
    private val context: Context
) {
    @SuppressLint("MissingPermission")
    fun startRecording(config: AudioConfig): Flow<ShortArray> = flow {
        val minBufferSize = AudioRecord.getMinBufferSize(
            config.sampleRate,
            config.channelConfig,
            config.audioFormat
        )
        val bufferSize = minBufferSize * 2

        val audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            config.sampleRate,
            config.channelConfig,
            config.audioFormat,
            bufferSize
        )

        if (audioRecord.state != AudioRecord.STATE_INITIALIZED) {
            throw IllegalStateException("AudioRecord initialization failed")
        }

        audioRecord.startRecording()
        try {
            val buffer = ShortArray(bufferSize / 2) // 16-bit PCM
            while (true) {
                val readResult = audioRecord.read(buffer, 0, buffer.size)
                if (readResult > 0) {
                    val data = buffer.copyOf(readResult)
                    emit(data)
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
