package com.example.hotelwallet.domain.repository

import com.example.hotelwallet.domain.model.Message
import com.example.hotelwallet.domain.model.User
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    suspend fun getLogin(email: String, password: String): Flow<Resource<User>>

    suspend fun signUp(
        name: String,
        prenom: String,
        email: String,
        password: String
    ): Flow<Resource<Message>>

    suspend fun logout(): Flow<Resource<Boolean>>
}