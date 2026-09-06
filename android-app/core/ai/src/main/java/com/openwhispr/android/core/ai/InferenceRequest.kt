package com.openwhispr.android.core.ai

data class InferenceRequest(
    val endpoint: String,
    val apiKey: String,
    val model: String,
    val text: String,
    val agentName: String?,
    val systemPrompt: String?,
    val maxTokens: Int?,
    val temperature: Float? = null
)
