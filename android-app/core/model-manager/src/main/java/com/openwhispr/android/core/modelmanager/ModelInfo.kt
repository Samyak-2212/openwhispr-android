package com.openwhispr.android.core.modelmanager

data class ModelInfo(
    val id: String,
    val name: String,
    val sizeBytes: Long,
    val type: String, // "whisper" or "sherpa"
    val downloadUrl: String,
    val minRamRequirementBytes: Long
)
