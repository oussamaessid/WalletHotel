package com.example.hotelwallet.domain.usecase.cart_usecase

import com.example.hotelwallet.domain.model.SubMenu
import com.example.hotelwallet.domain.repository.MenuRepository
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartProductUseCase @Inject constructor(
    private val repository: MenuRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<SubMenu>>> = repository.getCartProductList()
}