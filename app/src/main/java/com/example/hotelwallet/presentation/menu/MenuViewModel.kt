package com.example.hotelwallet.presentation.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelwallet.domain.model.Menu
import com.example.hotelwallet.domain.model.SubMenu
import com.example.hotelwallet.domain.usecase.favorite_usecase.GetFavoriteProductUseCase
import com.example.hotelwallet.domain.usecase.menu_usecase.DeleteProductUseCase
import com.example.hotelwallet.domain.usecase.menu_usecase.GetMenuUseCase
import com.example.hotelwallet.domain.usecase.menu_usecase.GetSubMenuUseCase
import com.example.hotelwallet.domain.usecase.menu_usecase.InsertProductUseCase
import com.example.hotelwallet.utility.Resource
import com.example.hotelwallet.utility.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getMenuUseCase: GetMenuUseCase,
    private val getSubMenuUseCase: GetSubMenuUseCase,
    private val insertProductUseCase: InsertProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
) : ViewModel() {

    private val _stateMenuList = MutableLiveData<Resource<List<Menu>>>()
    val stateMenuList: LiveData<Resource<List<Menu>>> get() = _stateMenuList

    private val _stateSubMenuList = MutableLiveData<Resource<List<SubMenu>>>()
    val stateSubMenuList: LiveData<Resource<List<SubMenu>>> get() = _stateSubMenuList

    private val _stateInsertProduct = SingleLiveEvent<Resource<Unit>>()
    val stateInsertProduct: LiveData<Resource<Unit>> get() = _stateInsertProduct

    private val _stateDeleteProduct = SingleLiveEvent<Resource<Unit>>()
    val stateDeleteProduct: LiveData<Resource<Unit>> get() = _stateDeleteProduct

    fun getMenuList(serviceId: Int) {
        viewModelScope.launch {
            getMenuUseCase(serviceId)
                .onEach {
                    _stateMenuList.value = it
                }.launchIn(viewModelScope)
        }
    }

    fun getSubMenuList(menuId: Int) {
        viewModelScope.launch {
            getSubMenuUseCase(menuId)
                .onEach {
                    _stateSubMenuList.value = it
                }.launchIn(viewModelScope)
        }
    }

    fun insertProduct(product: SubMenu){
        viewModelScope.launch {
            insertProductUseCase.invoke(product).onEach {
                _stateInsertProduct.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun deleteProduct(product: SubMenu){
        viewModelScope.launch {
            deleteProductUseCase.invoke(product).onEach {
                _stateDeleteProduct.value = it
            }.launchIn(viewModelScope)
        }
    }
}