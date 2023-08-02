package com.example.hotelwallet.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelwallet.domain.model.User
import com.example.hotelwallet.domain.usecase.profile_usecase.GetProfileUseCase
import com.example.hotelwallet.utility.Resource
import com.example.hotelwallet.utility.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUserCase: GetProfileUseCase,
//    private val updateProfileUserCase: UpdateProfileUseCase,
) : ViewModel() {

    private val _stateProfile =
        MutableStateFlow<Resource<User>>(Resource.Loading)
    val stateProfile: SharedFlow<Resource<User>> get() = _stateProfile

    private val _stateUpdateProfile =
        SingleLiveEvent<Resource<Pair<String, Boolean>>>()
    val stateUpdateProfile: LiveData<Resource<Pair<String, Boolean>>> get() = _stateUpdateProfile

    init {
        getProfile()
    }

    fun getProfile() {
        viewModelScope.launch {
            getProfileUserCase()
                .onEach {
                    _stateProfile.value = it
                }.launchIn(viewModelScope)
        }
    }

//    fun updateProfile(user: User) {
//        viewModelScope.launch {
//            updateProfileUserCase(user)
//                .onEach {
//                    _stateUpdateProfile.value = it
//                }.launchIn(viewModelScope)
//        }
//    }
}