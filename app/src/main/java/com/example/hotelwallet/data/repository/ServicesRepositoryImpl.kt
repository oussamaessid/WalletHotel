package com.example.hotelwallet.data.repository

import com.example.hotelwallet.data.mapper.ServiceMapper
import com.example.hotelwallet.data.source.remote.Api
import com.example.hotelwallet.domain.model.Services
import com.example.hotelwallet.domain.repository.ServiceRepository
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ServicesRepositoryImpl @Inject constructor(
    private val api: Api,
    private val serviceMapper: ServiceMapper,
) : ServiceRepository {
    override suspend fun getServicesByHotelId(hotelId: Int): Flow<Resource<List<Services>>> = flow {
        try {
            emit(Resource.Loading)
            val servicesResponse = serviceMapper.mapList(
                api.getServicesByHotel(hotelId).service
            )
            emit(Resource.Success(servicesResponse))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

}
