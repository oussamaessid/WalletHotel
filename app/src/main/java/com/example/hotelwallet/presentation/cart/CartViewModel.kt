package com.example.hotelwallet.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelwallet.domain.model.Order
import com.example.hotelwallet.domain.model.SubMenu
import com.example.hotelwallet.domain.usecase.cart_usecase.AddProductToOrderUseCase
import com.example.hotelwallet.domain.usecase.cart_usecase.GetCartProductUseCase
import com.example.hotelwallet.utility.Resource
import com.example.hotelwallet.utility.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getProductUseCase: GetCartProductUseCase,
    private val addProductToOrderUseCase: AddProductToOrderUseCase
) : ViewModel() {

    private val _stateProductList = MutableLiveData<Resource<List<SubMenu>>>()
    val stateProductList: LiveData<Resource<List<SubMenu>>> get() = _stateProductList

    private val _stateAddOrder = SingleLiveEvent<Resource<Unit>>()
    val stateAddOrder: LiveData<Resource<Unit>> get() = _stateAddOrder

    fun getProductList() {
        viewModelScope.launch {
            getProductUseCase()
                .onEach {
                    _stateProductList.value = it
                }.launchIn(viewModelScope)
        }
    }

    fun addProductToOrder(order: Order){
        viewModelScope.launch {
            addProductToOrderUseCase(order)
                .onEach {
                    _stateAddOrder.value = it
                }.launchIn(viewModelScope)
        }
    }
}