package com.example.hotelwallet.domain.repository

import com.example.hotelwallet.domain.model.Order
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun getOrderList(): Flow<Resource<List<Order>?>>
}