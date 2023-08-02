package com.example.hotelwallet.domain.usecase.authentication.login_usecase


import com.example.hotelwallet.domain.model.User
import com.example.hotelwallet.domain.repository.AuthenticationRepository
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(userName: String, password: String): Flow<Resource<User>> {
        return repository.getLogin(userName, password)
    }
}