package com.example.hotelwallet.data.source.remote

import com.example.hotelwallet.data.model.ChatMessageDto
import com.example.hotelwallet.domain.model.ChatRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface HotelApi {

    @POST("api")
    suspend fun getChatMessage(
        @Body msg: ChatRequest
    ): ChatMessageDto

}