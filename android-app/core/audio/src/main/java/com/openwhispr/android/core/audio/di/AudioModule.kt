package com.openwhispr.android.core.audio.di

import android.content.Context
import com.openwhispr.android.core.audio.AudioFileManager
import com.openwhispr.android.core.audio.AudioProcessor
import com.openwhispr.android.core.audio.AudioRecorder
import com.openwhispr.android.core.audio.MediaProjectionCapture
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AudioModule {

    @Provides
    @Singleton
    fun provideAudioRecorder(@ApplicationContext context: Context): AudioRecorder {
        return AudioRecorder(context)
    }

    @Provides
    @Singleton
    fun provideAudioProcessor(): AudioProcessor {
        return AudioProcessor()
    }

    @Provides
    @Singleton
    fun provideAudioFileManager(@ApplicationContext context: Context): AudioFileManager {
        return AudioFileManager(context)
    }

    @Provides
    @Singleton
    fun provideMediaProjectionCapture(): MediaProjectionCapture {
        return MediaProjectionCapture()
    }
}
