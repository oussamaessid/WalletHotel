package com.example.hotelwallet.domain.usecase.language_usecase

import android.media.MediaFormat.KEY_LANGUAGE
import com.example.hotelwallet.domain.repository.AppDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLanguageUseCase
@Inject constructor(
    private val appDataStore: AppDataStore
) {
    suspend operator fun invoke(): Flow<String?> = flow { emit(appDataStore.getStringValue(KEY_LANGUAGE))}
}