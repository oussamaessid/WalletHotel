package com.example.hotelwallet.domain.repository

import com.example.hotelwallet.domain.model.Plan
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow

interface GymRepository {
    suspend fun getGym(serviceId: Int): Flow<Resource<List<Plan>>>

}