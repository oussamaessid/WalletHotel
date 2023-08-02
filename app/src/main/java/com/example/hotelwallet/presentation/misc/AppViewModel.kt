package com.example.hotelwallet.presentation.misc

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelwallet.domain.repository.AppDataStore
import com.example.hotelwallet.utility.ACCESS_TOKEN
import com.example.hotelwallet.utility.KEY_HOTEL_ID
import com.example.hotelwallet.utility.KEY_LANGUAGE
import com.example.hotelwallet.utility.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val dataStore: AppDataStore
) : ViewModel() {

    private val _stateToken = SingleLiveEvent<String?>()
    val stateToken: LiveData<String?> get() = _stateToken

    private val _stateLang = SingleLiveEvent<String?>()
    val stateLang: LiveData<String?> get() = _stateLang

    private val _stateHotelId = SingleLiveEvent<Int?>()
    val stateHotelId: LiveData<Int?> get() = _stateHotelId

    init {
        getToken()
        getCurrentLang()
        getCurrentHotelId()
    }

    fun getToken() {
        viewModelScope.launch {
            _stateToken.value = dataStore.getStringValue(ACCESS_TOKEN)
        }
    }

    fun getCurrentLang() {
        viewModelScope.launch {
            _stateLang.value = dataStore.getStringValue(KEY_LANGUAGE)
        }
    }

    fun saveLanguage(language: String) {
        viewModelScope.launch {
            dataStore.saveStringValue(KEY_LANGUAGE, language)
            getCurrentLang()
        }
    }

    fun getCurrentHotelId() {
        viewModelScope.launch {
            _stateHotelId.value = dataStore.getIntValue(KEY_HOTEL_ID)
        }
    }

    fun setHotelId(hotelId: Int) {
        viewModelScope.launch {
            dataStore.saveIntValue(KEY_HOTEL_ID, hotelId)
        }
    }
}