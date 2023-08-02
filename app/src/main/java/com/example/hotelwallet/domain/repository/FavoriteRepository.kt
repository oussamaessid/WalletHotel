package com.example.hotelwallet.domain.repository

import com.example.hotelwallet.domain.model.SubMenu
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun insertProduct(product: SubMenu): Flow<Resource<Unit>>
    suspend fun deleteProduct(product: SubMenu): Flow<Resource<Unit>>
    suspend fun getFavoriteProductList(): Flow<Resource<List<SubMenu>?>>
}