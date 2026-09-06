package com.openwhispr.android.core.ai.sse

import com.openwhispr.android.core.ai.InferenceChunk
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import javax.inject.Inject

class SseClient @Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    fun stream(request: Request): Flow<InferenceChunk> = callbackFlow {
        val eventSourceListener = object : EventSourceListener() {
            override fun onEvent(eventSource: EventSource, id: String?, type: String?, data: String) {
                if (data == "[DONE]") {
                    trySend(InferenceChunk.Done)
                    close()
                    return
                }
                trySend(InferenceChunk.Text(data))
            }

            override fun onClosed(eventSource: EventSource) {
                trySend(InferenceChunk.Done)
                close()
            }

            override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
                val errorMsg = t?.message ?: "SSE stream failed with HTTP ${response?.code}"
                trySend(InferenceChunk.Error(errorMsg, t))
                close(t)
            }
        }

        val factory = EventSources.createFactory(okHttpClient)
        val eventSource = factory.newEventSource(request, eventSourceListener)

        awaitClose {
            eventSource.cancel()
        }
    }
}
