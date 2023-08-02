package com.example.hotelwallet.data.mapper

import com.example.hotelwallet.data.model.ServiceDto
import com.example.hotelwallet.domain.model.Services
import javax.inject.Inject

class ServiceMapper @Inject constructor() :
    BaseMapper<ServiceDto, Services> {
    override fun map(from: ServiceDto): Services {
        return Services(
            id = from.id,
            nom = from.nom,
            description = from.description,
            image = from.image,
            type = from.type
        )
    }

    override fun mapInverse(from: Services): ServiceDto {
        return ServiceDto(
            id = from.id,
            nom = from.nom,
            description = from.description,
            image = from.image,
            type = from.type
        )
    }
}