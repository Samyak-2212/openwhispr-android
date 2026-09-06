package com.openwhispr.android.feature.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor() : ViewModel() {

    private val _notes = MutableLiveData<List<String>>(emptyList())
    val notes: LiveData<List<String>> = _notes

    fun addNote(note: String) {
        val currentList = _notes.value.orEmpty().toMutableList()
        currentList.add(note)
        _notes.value = currentList
    }
}
