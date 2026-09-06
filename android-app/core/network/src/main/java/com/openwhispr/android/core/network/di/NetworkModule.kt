package com.openwhispr.android.core.network.di

import android.content.Context
import com.openwhispr.android.core.network.api.OpenWhisprApi
import com.openwhispr.android.core.network.auth.AuthClient
import com.openwhispr.android.core.network.auth.AuthInterceptor
import com.openwhispr.android.core.network.auth.TokenManager
import com.openwhispr.android.core.network.sync.ConflictResolver
import com.openwhispr.android.core.network.sync.SyncEngine
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun provideAuthClient(@ApplicationContext context: Context, tokenManager: TokenManager): AuthClient {
        return AuthClient(context, tokenManager)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
        return AuthInterceptor(tokenManager)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openwhispr.com/") // Assuming standard API endpoint
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenWhisprApi(retrofit: Retrofit): OpenWhisprApi {
        return retrofit.create(OpenWhisprApi::class.java)
    }
    
    @Provides
    @Singleton
    fun provideConflictResolver(): ConflictResolver {
        return ConflictResolver()
    }
    
    @Provides
    @Singleton
    fun provideSyncEngine(api: OpenWhisprApi, conflictResolver: ConflictResolver): SyncEngine {
        return SyncEngine(api, conflictResolver)
    }
}
