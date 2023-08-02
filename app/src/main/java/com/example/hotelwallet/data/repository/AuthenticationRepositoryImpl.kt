package com.example.hotelwallet.data.repository

import android.annotation.SuppressLint
import com.example.hotelwallet.data.mapper.UserMapper
import com.example.hotelwallet.data.mapper.MessageMapper
import com.example.hotelwallet.data.source.remote.Api
import com.example.hotelwallet.domain.model.LoginRequest
import com.example.hotelwallet.domain.model.Message
import com.example.hotelwallet.domain.model.SignUp
import com.example.hotelwallet.domain.model.User
import com.example.hotelwallet.domain.repository.AppDataStore
import com.example.hotelwallet.domain.repository.AuthenticationRepository
import com.example.hotelwallet.utility.ACCESS_TOKEN
import com.example.hotelwallet.utility.BEARER
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val api: Api,
    private val userMapper: UserMapper,
    private val messageMapper: MessageMapper,
    private val dataStore: AppDataStore
) : AuthenticationRepository {

    @SuppressLint("SuspiciousIndentation")
    override suspend fun getLogin(email: String, password: String): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading)
            api.login(LoginRequest(email, password)).let { loginResponse ->
                dataStore.saveStringValue(ACCESS_TOKEN, loginResponse.accessToken)
                val userResponse = userMapper.map(loginResponse.user)
                emit(Resource.Success(userResponse))
            }
        } catch (e: HttpException) {
            val message = if (e.code() == 401){
                "Votre compte n'est pas encore validé. Veuillez contacter l'administrateur."
            }else{
                e.message
            }
            emit(Resource.Error(message ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    override suspend fun signUp(
        name: String,
        prenom: String,
        email: String,
        password: String
    ): Flow<Resource<Message>> = flow {
        val registerRequest = SignUp(name, prenom, email, password)

        try {
            emit(Resource.Loading)
            val response = messageMapper.map(
                api.signUp(registerRequest)
            )
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    override suspend fun logout(): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading)
            val logoutResponse = api.logout("$BEARER ${dataStore.getStringValue(ACCESS_TOKEN)}")
            if (!logoutResponse.message.isNullOrEmpty()) {
                dataStore.deleteAllPreferences()
                emit(Resource.Success(true))
            } else {
                emit(Resource.Success(false))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
