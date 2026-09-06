package com.openwhispr.android.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Centralized permission manager for OpenWhispr.
 */
class PermissionManager(private val context: Context) {

    /**
     * Checks if all required permissions are granted.
     */
    fun hasRequiredPermissions(): Boolean {
        var granted = true
        
        granted = granted && hasPermission(Manifest.permission.RECORD_AUDIO)
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            granted = granted && hasPermission(Manifest.permission.POST_NOTIFICATIONS)
        }
        
        return granted
    }

    private fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Requests required permissions from the user.
     */
    fun requestPermissions(activity: Activity, requestCode: Int) {
        val permissions = mutableListOf<String>()
        
        if (!hasPermission(Manifest.permission.RECORD_AUDIO)) {
            permissions.add(Manifest.permission.RECORD_AUDIO)
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasPermission(Manifest.permission.POST_NOTIFICATIONS)) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }
        
        if (permissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(activity, permissions.toTypedArray(), requestCode)
        }
    }
}
