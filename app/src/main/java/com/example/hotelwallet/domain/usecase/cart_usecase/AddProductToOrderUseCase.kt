package com.example.hotelwallet.domain.usecase.cart_usecase

import com.example.hotelwallet.domain.model.Order
import com.example.hotelwallet.domain.repository.MenuRepository
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddProductToOrderUseCase @Inject constructor(
    private val repository: MenuRepository
) {
    suspend operator fun invoke(order: Order): Flow<Resource<Unit>> = repository.addProductToOrder(order)
}