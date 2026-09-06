package com.openwhispr.android.core.ai.provider

import com.openwhispr.android.core.ai.InferenceChunk
import com.openwhispr.android.core.ai.InferenceProvider
import com.openwhispr.android.core.ai.InferenceRequest
import com.openwhispr.android.core.ai.InferenceResponse
import com.openwhispr.android.core.ai.model.ModelInfo
import com.openwhispr.android.core.ai.sse.SseClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class OpenAiProvider @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val sseClient: SseClient
) : InferenceProvider {

    override val providerId: String = "openai"
    
    // In a real app this would read from ModelRegistry
    override val supportedModels: List<ModelInfo> = emptyList()

    override suspend fun complete(request: InferenceRequest): Flow<InferenceChunk> {
        val payload = createPayload(request, stream = true)
        val req = Request.Builder()
            .url(request.endpoint)
            .post(payload.toString().toRequestBody("application/json".toMediaType()))
            .addHeader("Authorization", "Bearer ${request.apiKey}")
            .addHeader("Accept", "text/event-stream")
            .build()

        return sseClient.stream(req).map { chunk ->
            when (chunk) {
                is InferenceChunk.Text -> {
                    try {
                        val json = JSONObject(chunk.content)
                        val choices = json.optJSONArray("choices")
                        if (choices != null && choices.length() > 0) {
                            val delta = choices.getJSONObject(0).optJSONObject("delta")
                            val content = delta?.optString("content", "") ?: ""
                            if (content.isNotEmpty()) {
                                InferenceChunk.Text(content)
                            } else {
                                InferenceChunk.Text("") // Skip empty
                            }
                        } else {
                            InferenceChunk.Text("")
                        }
                    } catch (e: Exception) {
                        InferenceChunk.Error("Failed to parse OpenAI chunk", e)
                    }
                }
                else -> chunk
            }
        }
    }

    override suspend fun completeSync(request: InferenceRequest): InferenceResponse {
        val payload = createPayload(request, stream = false)
        val req = Request.Builder()
            .url(request.endpoint)
            .post(payload.toString().toRequestBody("application/json".toMediaType()))
            .addHeader("Authorization", "Bearer ${request.apiKey}")
            .addHeader("Accept", "application/json")
            .build()

        okHttpClient.newCall(req).execute().use { response ->
            if (!response.isSuccessful) throw Exception("HTTP ${response.code}: ${response.body?.string()}")
            val body = response.body?.string() ?: throw Exception("Empty response body")
            val json = JSONObject(body)
            val choices = json.optJSONArray("choices")
            val content = choices?.optJSONObject(0)?.optJSONObject("message")?.optString("content") ?: ""
            return InferenceResponse(content)
        }
    }

    private fun createPayload(request: InferenceRequest, stream: Boolean): JSONObject {
        val json = JSONObject()
        json.put("model", request.model)
        json.put("stream", stream)
        
        val messages = JSONArray()
        if (request.systemPrompt != null) {
            val sysMsg = JSONObject().apply {
                put("role", "system")
                put("content", request.systemPrompt)
            }
            messages.put(sysMsg)
        }
        val userMsg = JSONObject().apply {
            put("role", "user")
            put("content", request.text)
        }
        messages.put(userMsg)
        
        json.put("messages", messages)
        
        request.maxTokens?.let { json.put("max_tokens", it) }
        request.temperature?.let { json.put("temperature", it) }
        
        return json
    }
}
