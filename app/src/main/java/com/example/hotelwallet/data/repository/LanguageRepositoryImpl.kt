package com.example.hotelwallet.data.repository

import com.example.hotelwallet.domain.repository.AppDataStore
import com.example.hotelwallet.domain.repository.LanguageRepository
import com.example.hotelwallet.utility.KEY_LANGUAGE
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    val dataStore: AppDataStore
): LanguageRepository {

    override suspend fun setLanguage(lang: String) {
        dataStore.saveStringValue(KEY_LANGUAGE, lang)
    }
}