package com.example.hotelwallet.data.mapper

import com.example.hotelwallet.data.model.MenuDto
import com.example.hotelwallet.domain.model.Menu
import javax.inject.Inject

class MenuMapper @Inject constructor() :
    BaseMapper<MenuDto, Menu> {
    override fun map(from: MenuDto): Menu {
        return Menu(
            id = from.id,
            nom = from.nom,
            description = from.description,
            idService = from.idService
        )
    }

    override fun mapInverse(from: Menu): MenuDto {
        return MenuDto(
            id = from.id,
            nom = from.nom,
            description = from.description,
            idService = from.idService
        )
    }
}