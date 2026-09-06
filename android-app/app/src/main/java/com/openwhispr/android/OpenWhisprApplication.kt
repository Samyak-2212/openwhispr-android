package com.openwhispr.android

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

/**
 * OpenWhispr Application class.
 * Initializes Hilt DI and creates notification channels.
 */
@HiltAndroidApp
class OpenWhisprApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    /**
     * Create notification channels for API 26+.
     * On API 24-25, notifications work without channels via NotificationCompat.
     */
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NotificationManager::class.java)

            val recordingChannel = NotificationChannel(
                CHANNEL_RECORDING,
                "Recording",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Active recording indicator"
                setShowBadge(false)
            }

            val meetingChannel = NotificationChannel(
                CHANNEL_MEETING_DETECTED,
                "Meeting Detection",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Meeting detection alerts"
            }

            val downloadChannel = NotificationChannel(
                CHANNEL_MODEL_DOWNLOAD,
                "Model Downloads",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Model download progress"
                setShowBadge(false)
            }

            val transcriptionChannel = NotificationChannel(
                CHANNEL_TRANSCRIPTION_COMPLETE,
                "Transcription",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Transcription result notifications"
            }

            notificationManager.createNotificationChannels(
                listOf(recordingChannel, meetingChannel, downloadChannel, transcriptionChannel)
            )
        }
    }

    companion object {
        const val CHANNEL_RECORDING = "recording"
        const val CHANNEL_MEETING_DETECTED = "meeting_detected"
        const val CHANNEL_MODEL_DOWNLOAD = "model_download"
        const val CHANNEL_TRANSCRIPTION_COMPLETE = "transcription_complete"
    }
}
