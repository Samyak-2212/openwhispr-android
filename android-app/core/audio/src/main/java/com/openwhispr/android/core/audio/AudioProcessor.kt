package com.openwhispr.android.core.audio

import com.openwhispr.android.core.audio.model.AudioLevel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.sqrt

/**
 * Processes audio streams for levels and VAD.
 */
class AudioProcessor @Inject constructor() {
    
    /**
     * Maps a flow of PCM data to a flow of pairs containing the PCM data and its computed audio level.
     */
    fun processLevels(audioStream: Flow<ShortArray>): Flow<Pair<ShortArray, AudioLevel>> {
        return audioStream.map { buffer ->
            var sumSquare = 0.0f
            var maxPeak = 0.0f
            for (sample in buffer) {
                val normalized = sample / 32768.0f // Normalize to [-1.0, 1.0]
                sumSquare += normalized * normalized
                val absVal = Math.abs(normalized)
                if (absVal > maxPeak) {
                    maxPeak = absVal
                }
            }
            val rms = if (buffer.isNotEmpty()) sqrt(sumSquare / buffer.size) else 0.0f
            Pair(buffer, AudioLevel(rms, maxPeak))
        }
    }

    /**
     * Simple Voice Activity Detection (VAD).
     * @param threshold The RMS threshold to consider as voice activity.
     */
    fun isVoiceActive(level: AudioLevel, threshold: Float = 0.05f): Boolean {
        return level.rms > threshold
    }
}
