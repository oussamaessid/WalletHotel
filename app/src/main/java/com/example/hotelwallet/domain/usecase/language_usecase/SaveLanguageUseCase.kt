package com.example.hotelwallet.domain.usecase.language_usecase

import com.example.hotelwallet.domain.repository.LanguageRepository
import javax.inject.Inject

class SaveLanguageUseCase
@Inject constructor(
    private val repository: LanguageRepository
) {
    suspend operator fun invoke(language: String) {
        repository.setLanguage(language)
    }
}