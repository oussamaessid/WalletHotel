package com.example.hotelwallet.data.mapper

import com.example.hotelwallet.data.model.SubMenuDto
import com.example.hotelwallet.domain.model.SubMenu
import javax.inject.Inject

class SubMenuMapper @Inject constructor() :
    BaseMapper<SubMenuDto, SubMenu> {
    override fun map(from: SubMenuDto): SubMenu {
        return SubMenu(
            id = from.id,
            name = from.name,
            description = from.description,
            image = from.image,
            price = from.price,
            category = from.category,
            createdAt = from.createdAt,
            updatedAt = from.updatedAt,
            idMenu = from.idMenu,
            quantity = from.quantity
        )
    }

    override fun mapInverse(from: SubMenu): SubMenuDto {
        return SubMenuDto(
            id = from.id,
            name = from.name,
            description = from.description,
            image = from.image,
            price = from.price,
            category = from.category,
            createdAt = from.createdAt,
            updatedAt = from.updatedAt,
            idMenu = from.idMenu,
            quantity = from.quantity
        )
    }
}