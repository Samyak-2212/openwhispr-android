package com.openwhispr.android.ime

import android.inputmethodservice.InputMethodService
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.openwhispr.android.R

/**
 * Custom InputMethodService for OpenWhispr voice typing.
 */
class OpenWhisprIME : InputMethodService() {
    
    private var isDictating = false
    private lateinit var micButton: ImageButton
    
    override fun onCreateInputView(): View {
        val view = layoutInflater.inflate(R.layout.keyboard_view, null)
        micButton = view.findViewById(R.id.btn_mic)
        
        micButton.setOnClickListener {
            toggleDictation()
        }
        
        return view
    }
    
    private fun toggleDictation() {
        isDictating = !isDictating
        if (isDictating) {
            // Start dictation
            micButton.setImageResource(android.R.drawable.presence_audio_online) // placeholder
        } else {
            // Stop dictation
            micButton.setImageResource(android.R.drawable.presence_audio_away)
        }
    }
    
    /**
     * Commits the transcribed text to the active input connection.
     */
    fun commitText(text: String) {
        currentInputConnection?.commitText(text, 1)
    }
}
