package com.openwhispr.android.feature.dictation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DictationViewModel @Inject constructor() : ViewModel() {

    data class TranscriptionState(val text: String, val isRecording: Boolean)

    private val _transcriptionState = MutableLiveData(TranscriptionState("Listening...", false))
    val transcriptionState: LiveData<TranscriptionState> = _transcriptionState

    fun toggleRecording() {
        val current = _transcriptionState.value ?: return
        _transcriptionState.value = current.copy(isRecording = !current.isRecording)
    }
}
