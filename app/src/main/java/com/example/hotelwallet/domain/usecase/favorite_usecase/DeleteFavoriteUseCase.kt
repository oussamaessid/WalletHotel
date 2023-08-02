package com.example.hotelwallet.domain.usecase.favorite_usecase

import com.example.hotelwallet.domain.model.SubMenu
import com.example.hotelwallet.domain.repository.FavoriteRepository
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(product: SubMenu): Flow<Resource<Unit>> =
        repository.deleteProduct(product)
}