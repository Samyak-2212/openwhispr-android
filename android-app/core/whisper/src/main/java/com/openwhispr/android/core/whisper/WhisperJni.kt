package com.openwhispr.android.core.whisper

object WhisperJni {
    init {
        System.loadLibrary("whisper")
    }

    external fun initContext(modelPath: String): Long
    external fun transcribe(contextPtr: Long, audioData: FloatArray): String
    external fun freeContext(contextPtr: Long)
}
