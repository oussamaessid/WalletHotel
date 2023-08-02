package com.example.hotelwallet.domain.usecase.authentication.register_use_case

import com.example.hotelwallet.domain.model.Message
import com.example.hotelwallet.domain.repository.AuthenticationRepository
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signRepository: AuthenticationRepository
) {
    suspend operator fun invoke(
        name: String,
        prenom: String,
        email: String,
        password: String
    ): Flow<Resource<Message>> =
        signRepository.signUp(
            name,
            prenom,
            email,
            password
        )
}