package com.example.hotelwallet.data.model

import com.google.gson.annotations.SerializedName
import java.time.OffsetDateTime


data class UserDto(
    val id: Int,
    @SerializedName("nom")
    val lastName:String,
    @SerializedName("prenom")
    val firstName: String,
    val photo:String,
    val email: String,
    val role:String,
    @SerializedName("statut")
    val status:String,
    @SerializedName("solde")
    val balance:Double,
    @SerializedName("id_hotel")
    val hotelId: Int,
    @SerializedName("id_service")
    val serviceId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)