package com.example.hotelwallet.domain.repository

import com.example.hotelwallet.domain.model.Menu
import com.example.hotelwallet.domain.model.Order
import com.example.hotelwallet.domain.model.SubMenu
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    suspend fun getMenuByServiceId(serviceId: Int): Flow<Resource<List<Menu>>>
    suspend fun getSubMenuByMenuId(menuId: Int): Flow<Resource<List<SubMenu>>>
    suspend fun insertProduct(product: SubMenu): Flow<Resource<Unit>>
    suspend fun deleteProduct(product: SubMenu): Flow<Resource<Unit>>
    suspend fun getCartProductList(): Flow<Resource<List<SubMenu>>>
    suspend fun addProductToOrder(order: Order): Flow<Resource<Unit>>
}