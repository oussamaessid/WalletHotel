package com.example.hotelwallet.domain.model


data class SignUp(
    val nom:String,
    val prenom:String,
    val email:String,
    val password:String,
    val id_hotel : Int = 1
)