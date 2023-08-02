package com.example.hotelwallet.data.repository

import com.example.hotelwallet.data.mapper.ChatMapper
import com.example.hotelwallet.data.source.remote.HotelApi
import com.example.hotelwallet.domain.model.ChatMessage
import com.example.hotelwallet.domain.model.ChatRequest
import com.example.hotelwallet.domain.repository.ChatRepository
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val api: HotelApi,
    private val chatMapper: ChatMapper,
) : ChatRepository {
    override suspend fun getChat(msg: String): Flow<Resource<ChatMessage>>  =
        flow {
        try {
            emit(Resource.Loading)
            val servicesResponse = chatMapper.map(
                api.getChatMessage(ChatRequest(msg))
            )
            emit(Resource.Success(servicesResponse))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

}
