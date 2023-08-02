package com.example.hotelwallet.domain.usecase.menu_usecase

import com.example.hotelwallet.domain.model.SubMenu
import com.example.hotelwallet.domain.repository.MenuRepository
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertProductUseCase @Inject constructor(
    private val repository: MenuRepository
) {
    suspend operator fun invoke(product: SubMenu): Flow<Resource<Unit>> =
        repository.insertProduct(product)
}