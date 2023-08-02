package com.example.hotelwallet.data.mapper

import com.example.hotelwallet.data.model.FavoriteEntity
import com.example.hotelwallet.domain.model.SubMenu
import javax.inject.Inject

class FavoriteMapper @Inject constructor() :
    BaseMapper<FavoriteEntity, SubMenu> {
    override fun map(from: FavoriteEntity): SubMenu {
        return SubMenu(
            id = from.id,
            name = from.name,
            description = from.description,
            image = from.image,
            price = from.price,
            category = from.category,
            createdAt = from.createdAt,
            updatedAt = from.updatedAt,
            idMenu = from.idMenu
        )
    }

    override fun mapInverse(from: SubMenu): FavoriteEntity {
        return FavoriteEntity(
            id = from.id,
            name = from.name,
            description = from.description,
            image = from.image,
            price = from.price,
            category = from.category,
            createdAt = from.createdAt,
            updatedAt = from.updatedAt,
            idMenu = from.idMenu
        )
    }
}