package com.example.hotelwallet.domain.repository

import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    suspend fun setLanguage(lang: String)
}