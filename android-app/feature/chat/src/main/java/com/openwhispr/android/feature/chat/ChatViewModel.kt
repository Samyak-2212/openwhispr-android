package com.openwhispr.android.feature.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {
    private val _messages = MutableLiveData<List<String>>(emptyList())
    val messages: LiveData<List<String>> = _messages
}
