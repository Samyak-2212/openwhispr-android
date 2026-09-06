package com.openwhispr.android.core.ai.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CloudProvider(
    val id: String,
    val name: String,
    val models: List<ModelInfo>,
    val defaultModel: String? = null
)

@JsonClass(generateAdapter = true)
data class LocalProvider(
    val id: String,
    val name: String,
    val baseUrl: String?,
    val models: List<LocalModelInfo>
)

@JsonClass(generateAdapter = true)
data class LocalModelInfo(
    val id: String,
    val name: String,
    val size: String,
    val sizeBytes: Long,
    val description: String,
    val fileName: String,
    val quantization: String,
    val hfRepo: String
)

@JsonClass(generateAdapter = true)
data class ModelRegistryData(
    val cloudProviders: List<CloudProvider>,
    val localProviders: List<LocalProvider>,
    val enterpriseProviders: List<CloudProvider>
)
