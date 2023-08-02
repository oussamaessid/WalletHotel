package com.example.hotelwallet.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelwallet.domain.model.SubMenu
import com.example.hotelwallet.domain.usecase.favorite_usecase.DeleteFavoriteUseCase
import com.example.hotelwallet.domain.usecase.favorite_usecase.GetFavoriteProductUseCase
import com.example.hotelwallet.domain.usecase.favorite_usecase.InsertFavoriteUseCase
import com.example.hotelwallet.utility.Resource
import com.example.hotelwallet.utility.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val getFavoriteUseCase: GetFavoriteProductUseCase,
) : ViewModel() {
    private val _stateFavoriteList = MutableLiveData<Resource<List<SubMenu>?>>()
    val stateFavoriteList: LiveData<Resource<List<SubMenu>?>> get() = _stateFavoriteList

    private val _stateInsertFavorite = SingleLiveEvent<Resource<Unit>>()
    val stateInsertFavorite: LiveData<Resource<Unit>> get() = _stateInsertFavorite

    private val _stateDeleteFavorite = SingleLiveEvent<Resource<Unit>>()
    val stateDeleteFavorite: LiveData<Resource<Unit>> get() = _stateDeleteFavorite

    fun getFavoriteList() {
        viewModelScope.launch {
            getFavoriteUseCase()
                .onEach {
                    _stateFavoriteList.value = it
                }.launchIn(viewModelScope)
        }
    }

    fun insertFavorite(product: SubMenu) {
        viewModelScope.launch {
            insertFavoriteUseCase.invoke(product).onEach {
                _stateInsertFavorite.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun deleteFavorite(product: SubMenu) {
        viewModelScope.launch {
            deleteFavoriteUseCase.invoke(product).onEach {
                _stateDeleteFavorite.value = it
            }.launchIn(viewModelScope)
        }
    }
}