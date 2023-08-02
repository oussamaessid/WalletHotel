package com.example.hotelwallet.data.model

import com.google.gson.annotations.SerializedName

data class ImageDto(
    val id: Int,
    @SerializedName("plan_id")
    val planId: Int,
    @SerializedName("image_path")
    val imagePath: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)
