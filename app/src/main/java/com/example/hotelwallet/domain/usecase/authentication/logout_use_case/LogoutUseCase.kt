package com.example.hotelwallet.domain.usecase.authentication.logout_use_case

import com.example.hotelwallet.domain.repository.AuthenticationRepository
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogoutUseCase
@Inject constructor(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(): Flow<Resource<Boolean>> =
        repository.logout()
}