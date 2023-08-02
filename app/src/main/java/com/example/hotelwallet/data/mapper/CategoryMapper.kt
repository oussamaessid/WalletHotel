package com.example.hotelwallet.data.mapper

import com.example.hotelwallet.data.model.CategoryDto
import com.example.hotelwallet.domain.model.Category
import javax.inject.Inject

class CategoryMapper @Inject constructor() :
    BaseMapper<CategoryDto, Category> {
    override fun map(from: CategoryDto): Category {
        return Category(
            id = from.id,
            nom = from.nom,
            description = from.description
        )
    }

    override fun mapInverse(from: Category): CategoryDto {
        return CategoryDto(
            id = from.id,
            nom = from.nom,
            description = from.description
        )
    }
}