package com.example.hotelwallet.data.model

import com.google.gson.annotations.SerializedName

data class PlanDto(
    val id: Int,
    @SerializedName("nom")
    val name: String,
    val description: String,
    @SerializedName("prix")
    val price: String,
    val image: String
)