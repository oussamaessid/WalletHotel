package com.example.hotelwallet.presentation.historic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelwallet.domain.model.Order
import com.example.hotelwallet.domain.usecase.order_usecase.GetOrderUseCase
import com.example.hotelwallet.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoricViewModel @Inject constructor(
    private val getOrderUseCase: GetOrderUseCase
) : ViewModel() {

    private val _stateOrderList = MutableLiveData<Resource<List<Order>?>>()
    val stateOrderList: LiveData<Resource<List<Order>?>> get() = _stateOrderList

    fun getOrderList() {
        viewModelScope.launch {
            getOrderUseCase()
                .onEach {
                    _stateOrderList.value = it
                }.launchIn(viewModelScope)
        }
    }
}