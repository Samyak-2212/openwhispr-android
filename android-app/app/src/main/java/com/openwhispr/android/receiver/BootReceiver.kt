package com.openwhispr.android.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * Broadcast receiver for handling device boot.
 */
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Check settings if recording service should start on boot
            // Start service if enabled
            Toast.makeText(context, "OpenWhispr ready", Toast.LENGTH_SHORT).show()
        }
    }
}
