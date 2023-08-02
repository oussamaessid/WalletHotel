package com.example.hotelwallet.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class SubMenuDto(
    @PrimaryKey
    val id: Int,
    @SerializedName("nom")
    val name: String,
    val description: String,
    @SerializedName("prix")
    val price: String,
    val image: String,
    @SerializedName("categorie")
    val category: String,
    @SerializedName("id_menu")
    val idMenu: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    var quantity: Int = 1,
    var isFavorite: Boolean = false,
    val isValid: Boolean = false
)