package com.openwhispr.android.core.ai.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModelInfo(
    val id: String,
    val name: String,
    val description: String?,
    val descriptionKey: String?,
    val tokenParam: String? = null,
    val supportsTemperature: Boolean? = null,
    val supportsVision: Boolean? = null,
    val supportsThinking: Boolean? = null
)
