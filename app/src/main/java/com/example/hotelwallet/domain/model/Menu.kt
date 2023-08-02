package com.example.hotelwallet.domain.model

data class Menu(
    val id: Int,
    val nom: String,
    val description: String,
    val idService: Int,
)