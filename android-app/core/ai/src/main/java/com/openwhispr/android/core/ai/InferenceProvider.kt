package com.openwhispr.android.core.ai

import com.openwhispr.android.core.ai.model.ModelInfo
import kotlinx.coroutines.flow.Flow

interface InferenceProvider {
    val providerId: String
    val supportedModels: List<ModelInfo>

    suspend fun complete(request: InferenceRequest): Flow<InferenceChunk>
    suspend fun completeSync(request: InferenceRequest): InferenceResponse
}
