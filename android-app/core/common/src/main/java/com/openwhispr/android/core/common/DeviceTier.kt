package com.openwhispr.android.core.common

import android.app.ActivityManager
import android.content.Context

/**
 * Device capability tiers based on available RAM.
 * Used to gate features like local Whisper models and local LLM inference.
 *
 * - CONSTRAINED (≤ 2 GB): Cloud-only, no local models
 * - LOW (2–4 GB): Whisper tiny only, no local LLM
 * - MID (4–6 GB): Whisper tiny/base, local LLM (small)
 * - HIGH (6 GB+): All Whisper models, local LLM
 */
enum class DeviceTier {
    CONSTRAINED,
    LOW,
    MID,
    HIGH;

    /** Whether local Whisper transcription is available on this tier */
    val supportsLocalWhisper: Boolean
        get() = this != CONSTRAINED

    /** Whether local LLM inference is available on this tier */
    val supportsLocalLLM: Boolean
        get() = this == MID || this == HIGH

    /** Whether speaker diarization (ONNX model) is available on this tier */
    val supportsDiarization: Boolean
        get() = this == MID || this == HIGH

    /** Maximum Whisper model size name allowed on this tier */
    val maxWhisperModel: String?
        get() = when (this) {
            CONSTRAINED -> null
            LOW -> "tiny"
            MID -> "base"
            HIGH -> "large"
        }
}

/**
 * Detects the device capability tier based on total RAM.
 * Call once at app startup and cache the result.
 */
object DeviceTierDetector {

    @Volatile
    private var cachedTier: DeviceTier? = null

    /**
     * Detect and cache the device tier.
     * Thread-safe — concurrent calls return the same cached value.
     */
    fun detect(context: Context): DeviceTier {
        cachedTier?.let { return it }

        synchronized(this) {
            cachedTier?.let { return it }

            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val memInfo = ActivityManager.MemoryInfo()
            activityManager.getMemoryInfo(memInfo)

            val totalRamMB = memInfo.totalMem / (1024 * 1024)

            val tier = when {
                totalRamMB <= 2048 -> DeviceTier.CONSTRAINED
                totalRamMB <= 4096 -> DeviceTier.LOW
                totalRamMB <= 6144 -> DeviceTier.MID
                else -> DeviceTier.HIGH
            }

            cachedTier = tier
            return tier
        }
    }

    /**
     * Get the cached tier, or detect if not yet cached.
     * @throws IllegalStateException if detect() has never been called
     */
    fun getCachedTier(): DeviceTier {
        return cachedTier ?: throw IllegalStateException(
            "DeviceTierDetector.detect() must be called before getCachedTier()"
        )
    }

    /** Total device RAM in MB */
    fun getTotalRamMB(context: Context): Long {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memInfo)
        return memInfo.totalMem / (1024 * 1024)
    }
}
