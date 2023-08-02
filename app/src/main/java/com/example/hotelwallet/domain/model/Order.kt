package com.example.hotelwallet.domain.model

data class Order(
    val id: Int = 0,
    val createdAt: Long,
    val totalPrice: Double,
    val category: Int,
    val platList: List<SubMenu> = emptyList(),
    val planList: List<Plan> = emptyList()
)
