package com.openwhispr.android.clipboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * Helper class for clipboard operations.
 */
class ClipboardHelper(private val context: Context) {

    /**
     * Copies the given text to the system clipboard.
     * @param text The text to copy.
     */
    fun copyToClipboard(text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("OpenWhispr Transcription", text)
        clipboard.setPrimaryClip(clip)
    }

    /**
     * Gets the current text from the clipboard.
     * @return The text from the clipboard, or null if empty.
     */
    fun getFromClipboard(): String? {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipboard.hasPrimaryClip()) {
            val clip = clipboard.primaryClip
            if (clip != null && clip.itemCount > 0) {
                return clip.getItemAt(0).text?.toString()
            }
        }
        return null
    }
}
