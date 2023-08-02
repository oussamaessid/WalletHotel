package com.example.hotelwallet.domain.usecase.profile_usecase


import com.example.hotelwallet.domain.model.User
import com.example.hotelwallet.domain.repository.ProfileRepository
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Flow<Resource<User>> {
        return repository.getProfile()
    }
}