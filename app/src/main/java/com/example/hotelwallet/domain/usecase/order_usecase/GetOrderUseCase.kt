package com.example.hotelwallet.domain.usecase.order_usecase

import com.example.hotelwallet.domain.model.Order
import com.example.hotelwallet.domain.repository.OrderRepository
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Order>?>> = repository.getOrderList()
}