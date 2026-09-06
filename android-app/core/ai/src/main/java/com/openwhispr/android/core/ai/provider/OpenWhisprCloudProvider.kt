package com.openwhispr.android.core.ai.provider

import com.openwhispr.android.core.ai.InferenceChunk
import com.openwhispr.android.core.ai.InferenceProvider
import com.openwhispr.android.core.ai.InferenceRequest
import com.openwhispr.android.core.ai.InferenceResponse
import com.openwhispr.android.core.ai.model.ModelInfo
import com.openwhispr.android.core.ai.sse.SseClient
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import javax.inject.Inject

class OpenWhisprCloudProvider @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val sseClient: SseClient
) : InferenceProvider {
    override val providerId: String = "openwhispr"
    override val supportedModels: List<ModelInfo> = emptyList()

    override suspend fun complete(request: InferenceRequest): Flow<InferenceChunk> {
        // Implementation for OpenWhispr Cloud
        TODO("Not yet implemented")
    }

    override suspend fun completeSync(request: InferenceRequest): InferenceResponse {
        TODO("Not yet implemented")
    }
}
