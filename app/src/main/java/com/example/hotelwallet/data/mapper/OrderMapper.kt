package com.example.hotelwallet.data.mapper

import com.example.hotelwallet.data.source.local.OrderEntity
import com.example.hotelwallet.domain.model.Order
import javax.inject.Inject

class OrderMapper @Inject constructor(
    private val planMapper: PlanMapper,
    private val subMenuMapper: SubMenuMapper
) :
    BaseMapper<OrderEntity, Order> {
    override fun map(from: OrderEntity): Order {
        return Order(
            id = from.id,
            createdAt = from.createdAt,
            totalPrice = from.totalPrice,
            category = from.category,
            platList = from.platList.let { subMenuMapper.mapList(it) },
            planList = from.planList.let { planMapper.mapList(it) }
        )
    }

    override fun mapInverse(from: Order): OrderEntity {
        return OrderEntity(
            id = from.id,
            createdAt = from.createdAt,
            totalPrice = from.totalPrice,
            category = from.category,
            platList = from.platList.let { subMenuMapper.mapListInverse(it) },
            planList = from.planList.let { planMapper.mapListInverse(it) }
        )
    }

}