package com.example.hotelwallet.domain.usecase.chat_usecase

import com.example.hotelwallet.domain.model.ChatMessage
import com.example.hotelwallet.domain.repository.ChatRepository
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatMessageUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(msg: String): Flow<Resource<ChatMessage>> =
        repository.getChat(msg)
}