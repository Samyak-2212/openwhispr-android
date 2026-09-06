package com.openwhispr.android.core.whisper.di

import com.openwhispr.android.core.whisper.WhisperEngine
import com.openwhispr.android.core.whisper.WhisperEngineImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WhisperModule {
    @Binds
    @Singleton
    abstract fun bindWhisperEngine(
        whisperEngineImpl: WhisperEngineImpl
    ): WhisperEngine
}
