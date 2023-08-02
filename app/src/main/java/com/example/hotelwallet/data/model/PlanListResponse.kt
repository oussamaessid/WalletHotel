package com.example.hotelwallet.data.model


data class PlanListResponse(
    val status: Int,
    val plans: List<PlanDto>
)