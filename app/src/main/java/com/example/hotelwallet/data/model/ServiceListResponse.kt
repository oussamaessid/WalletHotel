package com.example.hotelwallet.data.model


data class ServiceListResponse(
    val status: Int,
    val service: List<ServiceDto>
)