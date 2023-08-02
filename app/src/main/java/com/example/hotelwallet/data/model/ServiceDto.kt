package com.example.hotelwallet.data.model

import com.google.gson.annotations.SerializedName

data class ServiceDto (
    @SerializedName("id_service")
    val id: Int,
    val nom: String,
    val description: String,
    val image: String,
    val type : String
)