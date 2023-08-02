package com.example.hotelwallet.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("token")
    val accessToken: String,
    val user: UserDto,
    val status: Boolean
)