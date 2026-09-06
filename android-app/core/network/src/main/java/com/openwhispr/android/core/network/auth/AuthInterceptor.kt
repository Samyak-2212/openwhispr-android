package com.openwhispr.android.core.network.auth

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // Don't add token for public endpoints
        if (originalRequest.header("No-Auth") != null) {
            return chain.proceed(originalRequest)
        }

        val token = tokenManager.getToken()
        
        val requestBuilder = originalRequest.newBuilder()
        if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        val response = chain.proceed(requestBuilder.build())
        
        // Handle token refresh on 401
        if (response.code == 401) {
            // Need to handle token refresh logic here
            // This usually involves a synchronized block to prevent multiple refresh calls
        }
        
        return response
    }
}
