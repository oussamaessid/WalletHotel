package com.example.hotelwallet.data.repository

import android.util.Log
import com.example.hotelwallet.data.mapper.UserMapper
import com.example.hotelwallet.data.source.remote.Api
import com.example.hotelwallet.domain.model.User
import com.example.hotelwallet.domain.repository.AppDataStore
import com.example.hotelwallet.domain.repository.ProfileRepository
import com.example.hotelwallet.utility.ACCESS_TOKEN
import com.example.hotelwallet.utility.BEARER
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: Api,
    private val userMapper: UserMapper,
    private val dataStore: AppDataStore
): ProfileRepository {

    override suspend fun getProfile(): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading)
            Log.d("***TTT :", "$BEARER ${dataStore.getStringValue(ACCESS_TOKEN)}")
            val userResponse =
                userMapper.map(api.getProfile("$BEARER ${dataStore.getStringValue(ACCESS_TOKEN)}").user)
            emit(Resource.Success(userResponse))
        }catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}