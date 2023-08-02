package com.example.hotelwallet.data.mapper

import com.example.hotelwallet.data.model.ImageDto
import com.example.hotelwallet.domain.model.Image
import javax.inject.Inject

class ImageMapper @Inject constructor() :
    BaseMapper<ImageDto, Image> {
    override fun map(from: ImageDto): Image {
        return Image(
            id = from.id,
            planId = from.planId,
            imagePath = from.imagePath,
            createdAt = from.createdAt,
            updatedAt = from.updatedAt
        )
    }

    override fun mapInverse(from: Image): ImageDto {
        return ImageDto(
            id = from.id,
            planId = from.planId,
            imagePath = from.imagePath,
            createdAt = from.createdAt,
            updatedAt = from.updatedAt
        )
    }

}