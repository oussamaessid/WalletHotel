package com.example.hotelwallet.domain.model

data class Plan(
    val id: Int,
    val name: String,
    val description: String,
    val price: String,
    val image: String,
    var images :List<Image> = emptyList()
)