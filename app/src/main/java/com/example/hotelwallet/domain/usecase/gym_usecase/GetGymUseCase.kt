package com.example.hotelwallet.domain.usecase.gym_usecase

import com.example.hotelwallet.domain.model.Plan
import com.example.hotelwallet.domain.repository.GymRepository
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGymUseCase @Inject constructor(
    private val repository: GymRepository
) {
    suspend operator fun invoke(serviceId: Int): Flow<Resource<List<Plan>>> =
        repository.getGym(serviceId)
}