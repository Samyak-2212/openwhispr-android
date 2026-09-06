package com.openwhispr.android.core.ai

sealed class InferenceChunk {
    data class Text(val content: String) : InferenceChunk()
    data class Error(val message: String, val cause: Throwable? = null) : InferenceChunk()
    object Done : InferenceChunk()
}
