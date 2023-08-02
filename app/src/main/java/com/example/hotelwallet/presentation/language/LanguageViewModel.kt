package com.example.hotelwallet.presentation.language

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelwallet.domain.usecase.language_usecase.GetLanguageUseCase
import com.example.hotelwallet.domain.usecase.language_usecase.SaveLanguageUseCase
import com.example.hotelwallet.utility.KEY_ENGLISH
import com.example.hotelwallet.utility.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val saveLanguageUseCase: SaveLanguageUseCase,
    private val getLanguageUseCase: GetLanguageUseCase
) : ViewModel() {

    private val _stateLanguage = SingleLiveEvent<String?>()
    val stateLanguage: LiveData<String?> get() = _stateLanguage

    init {
        getLanguage()
    }

    fun getLanguage() {
        viewModelScope.launch {
            getLanguageUseCase()
                .onEach {
                    _stateLanguage.value = it
                }.launchIn(viewModelScope)
        }
    }

    fun saveLanguage(language: String) {
        viewModelScope.launch {
            saveLanguageUseCase(language)
            getLanguage()
        }
    }

}