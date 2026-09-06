package com.openwhispr.android.core.network.auth

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

class AuthClient(
    private val context: Context,
    private val tokenManager: TokenManager
) {
    private val authUrl = "https://auth.openwhispr.com"

    fun startSignInFlow() {
        val uri = Uri.parse("$authUrl/login")
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(context, uri)
    }
    
    fun startSignUpFlow() {
        val uri = Uri.parse("$authUrl/signup")
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(context, uri)
    }
    
    fun startForgotPasswordFlow() {
        val uri = Uri.parse("$authUrl/forgot-password")
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(context, uri)
    }
    
    fun handleAuthCallback(uri: Uri) {
        val token = uri.getQueryParameter("token")
        val refreshToken = uri.getQueryParameter("refresh_token")
        
        if (token != null) {
            tokenManager.saveToken(token)
        }
        if (refreshToken != null) {
            tokenManager.saveRefreshToken(refreshToken)
        }
    }
    
    fun signOut() {
        tokenManager.clearTokens()
    }
}
