package com.example.hotelwallet.domain.repository

import com.example.hotelwallet.domain.model.User
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfile(): Flow<Resource<User>>
}