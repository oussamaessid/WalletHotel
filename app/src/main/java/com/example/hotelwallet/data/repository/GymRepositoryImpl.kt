package com.example.hotelwallet.data.repository

import com.example.hotelwallet.data.mapper.ImageMapper
import com.example.hotelwallet.data.mapper.PlanMapper
import com.example.hotelwallet.data.source.remote.Api
import com.example.hotelwallet.domain.model.Plan
import com.example.hotelwallet.domain.repository.GymRepository
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GymRepositoryImpl @Inject constructor(
    private val api: Api,
    private val planMapper: PlanMapper,
    private val imageMapper: ImageMapper
) : GymRepository {
    override suspend fun getGym(serviceId: Int): Flow<Resource<List<Plan>>> = flow {
        try {
            emit(Resource.Loading)
            val planResponse = planMapper.mapList(
                api.getPlanByService(serviceId).plans
            )
            planResponse.map{plan ->
                plan.images = imageMapper.mapList(api.getImages(plan.id).images)
            }
            emit(Resource.Success(planResponse))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

}
