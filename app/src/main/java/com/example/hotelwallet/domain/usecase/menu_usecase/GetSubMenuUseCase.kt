package com.example.hotelwallet.domain.usecase.menu_usecase

import com.example.hotelwallet.domain.model.SubMenu
import com.example.hotelwallet.domain.repository.MenuRepository
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSubMenuUseCase @Inject constructor(
    private val repository: MenuRepository
) {
    suspend operator fun invoke(menuId: Int): Flow<Resource<List<SubMenu>>> =
        repository.getSubMenuByMenuId(menuId)
}