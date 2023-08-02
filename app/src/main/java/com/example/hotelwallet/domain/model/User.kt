package com.example.hotelwallet.domain.model

data class User(
    val id: Int,
    val lastName: String,
    val firstName: String,
    val photo: String,
    val email: String,
    val role: String,
    val status: String,
    val balance: Double,
    val hotelId: Int,
    val serviceId: Int,
    val createdAt: String,
    val updatedAt: String
)