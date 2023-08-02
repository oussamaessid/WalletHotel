package com.example.hotelwallet.domain.usecase.menu_usecase

import com.example.hotelwallet.domain.model.Menu
import com.example.hotelwallet.domain.repository.MenuRepository
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMenuUseCase @Inject constructor(
    private val repository: MenuRepository
) {
    suspend operator fun invoke(serviceId: Int): Flow<Resource<List<Menu>>> =
        repository.getMenuByServiceId(serviceId)
}