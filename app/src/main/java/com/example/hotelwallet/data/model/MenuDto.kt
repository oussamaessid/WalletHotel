package com.example.hotelwallet.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cart_items")
data class MenuDto(
    @PrimaryKey
    val id: Int,
    val nom: String,
    val description: String,
    @SerializedName("service_id")
    val idService: Int
)