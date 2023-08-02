package com.example.hotelwallet.data.mapper

import com.example.hotelwallet.data.model.PlanDto
import com.example.hotelwallet.domain.model.Plan
import javax.inject.Inject

class PlanMapper @Inject constructor() :
    BaseMapper<PlanDto, Plan> {
    override fun map(from: PlanDto): Plan {
        return Plan(
            id = from.id,
            name = from.name,
            description = from.description,
            price = from.price,
            image = from.image
        )
    }

    override fun mapInverse(from: Plan): PlanDto {
        return PlanDto(
            id = from.id,
            name = from.name,
            description = from.description,
            price = from.price,
            image = from.image
        )
    }

}