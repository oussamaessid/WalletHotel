package com.example.hotelwallet.presentation.gym


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelwallet.domain.model.Plan
import com.example.hotelwallet.domain.usecase.gym_usecase.GetGymUseCase
import com.example.hotelwallet.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymViewModel @Inject constructor(
    private val getGymUseCase: GetGymUseCase,
) : ViewModel() {

    private val _stateGyms = MutableLiveData<Resource<List<Plan>>>()
    val stateGyms: LiveData<Resource<List<Plan>>> get() = _stateGyms

    fun getGymList(serviceId: Int) {
        viewModelScope.launch {
            getGymUseCase(serviceId)
                .onEach {
                    _stateGyms.value = it
                }.launchIn(viewModelScope)
        }
    }

}