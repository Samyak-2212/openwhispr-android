package com.openwhispr.android.core.ai.di

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.openwhispr.android.core.ai.InferenceProvider
import com.openwhispr.android.core.ai.provider.AnthropicProvider
import com.openwhispr.android.core.ai.provider.GeminiProvider
import com.openwhispr.android.core.ai.provider.GroqProvider
import com.openwhispr.android.core.ai.provider.OpenAiProvider
import com.openwhispr.android.core.ai.provider.OpenWhisprCloudProvider
import com.openwhispr.android.core.ai.provider.TinfoilProvider
import com.openwhispr.android.core.ai.sse.SseClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import android.content.SharedPreferences

@Module
@InstallIn(SingletonComponent::class)
object AiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideSseClient(okHttpClient: OkHttpClient): SseClient {
        return SseClient(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
            
        return EncryptedSharedPreferences.create(
            context,
            "secret_api_keys",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    fun provideProviders(
        okHttpClient: OkHttpClient,
        sseClient: SseClient
    ): Map<String, InferenceProvider> {
        val providers = listOf(
            OpenAiProvider(okHttpClient, sseClient),
            AnthropicProvider(okHttpClient, sseClient),
            GeminiProvider(okHttpClient, sseClient),
            GroqProvider(okHttpClient, sseClient),
            TinfoilProvider(okHttpClient, sseClient),
            OpenWhisprCloudProvider(okHttpClient, sseClient)
        )
        return providers.associateBy { it.providerId }
    }
}
