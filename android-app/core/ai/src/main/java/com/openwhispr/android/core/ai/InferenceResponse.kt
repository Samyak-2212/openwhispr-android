package com.openwhispr.android.core.ai

data class InferenceResponse(
    val text: String,
    val usage: Usage? = null
) {
    data class Usage(
        val promptTokens: Int,
        val completionTokens: Int,
        val totalTokens: Int
    )
}
