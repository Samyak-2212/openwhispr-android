package com.openwhispr.android.core.modelmanager

import android.os.StatFs
import com.openwhispr.android.core.common.DeviceTier
import javax.inject.Inject

class ModelRepository @Inject constructor() {
    private val allModels = listOf(
        ModelInfo("tiny", "Whisper Tiny", 75 * 1024 * 1024L, "whisper", "https://huggingface.co/...", 512 * 1024 * 1024L),
        ModelInfo("base", "Whisper Base", 142 * 1024 * 1024L, "whisper", "https://huggingface.co/...", 1024 * 1024 * 1024L),
        ModelInfo("small", "Whisper Small", 466 * 1024 * 1024L, "whisper", "https://huggingface.co/...", 2L * 1024 * 1024 * 1024L)
    )

    fun getAvailableModels(tier: DeviceTier): List<ModelInfo> {
        return when (tier) {
            DeviceTier.CONSTRAINED -> emptyList()
            DeviceTier.LOW -> allModels.filter { it.id == "tiny" }
            DeviceTier.MID -> allModels.filter { it.id == "tiny" || it.id == "base" }
            DeviceTier.HIGH -> allModels
        }
    }

    fun checkStorageSpace(model: ModelInfo, path: String): Boolean {
        val stat = StatFs(path)
        val availableBytes = stat.availableBlocksLong * stat.blockSizeLong
        return availableBytes >= model.sizeBytes * 2
    }
}
