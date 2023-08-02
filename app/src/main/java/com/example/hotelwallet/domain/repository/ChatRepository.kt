package com.example.hotelwallet.domain.repository

import com.example.hotelwallet.domain.model.ChatMessage
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getChat(msg: String): Flow<Resource<ChatMessage>>
}