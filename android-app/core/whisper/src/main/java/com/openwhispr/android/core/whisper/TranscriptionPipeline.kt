package com.openwhispr.android.core.whisper

import android.os.Handler
import android.os.HandlerThread
import com.openwhispr.android.core.common.DeviceTier
import kotlinx.coroutines.android.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class TranscriptionPipeline @Inject constructor(
    private val whisperEngine: WhisperEngine,
    private val cloudClient: CloudTranscriptionClient
) {
    private val inferenceThread = HandlerThread("WhisperInference").apply { start() }
    private val inferenceDispatcher = Handler(inferenceThread.looper).asCoroutineDispatcher("WhisperDispatcher")

    suspend fun transcribe(
        audioPath: String,
        config: TranscriptionConfig,
        deviceTier: DeviceTier
    ): TranscriptionResult {
        return withContext(inferenceDispatcher) {
            try {
                withTimeout(5 * 60 * 1000L) { // 5 min max
                    if (deviceTier == DeviceTier.CONSTRAINED || !whisperEngine.isModelLoaded()) {
                        cloudClient.transcribe(audioPath, config)
                    } else {
                        whisperEngine.transcribe(audioPath, config)
                    }
                }
            } catch (e: Exception) {
                // Auto-fallback if local fails or times out (OOM etc)
                cloudClient.transcribe(audioPath, config)
            }
        }
    }
}
