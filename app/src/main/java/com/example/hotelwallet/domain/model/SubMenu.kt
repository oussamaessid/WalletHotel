package com.example.hotelwallet.domain.model

data class SubMenu(
    val id: Int,
    val name: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String,
    val idMenu: String,
    val createdAt: String,
    val updatedAt: String,
    var quantity: Int = 1,
    var isFavorite: Boolean = false
)