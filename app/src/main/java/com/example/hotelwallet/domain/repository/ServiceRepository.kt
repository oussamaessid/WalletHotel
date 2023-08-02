package com.example.hotelwallet.domain.repository

import com.example.hotelwallet.domain.model.Services
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {
    suspend fun getServicesByHotelId(hotelId: Int): Flow<Resource<List<Services>>>
}