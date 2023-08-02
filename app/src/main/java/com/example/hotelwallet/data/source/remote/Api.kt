package com.example.hotelwallet.data.source.remote

import com.example.hotelwallet.data.model.*
import com.example.hotelwallet.domain.model.LoginRequest
import com.example.hotelwallet.domain.model.SignUp
import retrofit2.http.*

interface Api {

    @GET("services/{hotel_id}")
    suspend fun getServicesByHotel(
        @Path("hotel_id") hotelId: Int
    ): ServiceListResponse

    @GET("menu/{service_id}")
    suspend fun getMenuByServiceId(
        @Path("service_id") serviceId: Int
    ): MenuListResponse

    @GET("plats/{id}")
    suspend fun getMenuItems(
        @Path("id") id: String
    ): MenuItemListResponse

    @GET("plans/{service_id}")
    suspend fun getPlanByService(
        @Path("service_id") serviceId: Int
    ): PlanListResponse


    @POST("auth/login")
    suspend fun login(@Body credentials: LoginRequest):  UserResponse

    @POST("auth/create")
    suspend fun signUp(@Body credentials: SignUp): MessageDto

    @GET("auth/profile")
    suspend fun getProfile(
        @Header("Authorization") accessToken: String
    ):  ProfileResponse

    @POST("auth/logout")
    suspend fun logout(
        @Header("Authorization") accessToken: String
    ):  MessageDto

    @GET("plan/{plan_id}")
    suspend fun getImages(
        @Path("plan_id") planId: Int
    ): ImageListResponse

}