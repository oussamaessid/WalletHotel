package com.example.hotelwallet.presentation.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelwallet.domain.model.ChatMessage
import com.example.hotelwallet.domain.usecase.chat_usecase.GetChatMessageUseCase
import com.example.hotelwallet.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getChatMessageUseCaseUseCase: GetChatMessageUseCase
) : ViewModel() {

    private val _stateChat = MutableLiveData<Resource<ChatMessage>>()
    val stateChat: LiveData<Resource<ChatMessage>> get() = _stateChat

    fun getChatMessage(msg: String) {
        viewModelScope.launch {
            getChatMessageUseCaseUseCase(msg)
                .onEach {
                    _stateChat.value = it
                }.launchIn(viewModelScope)
        }
    }
}