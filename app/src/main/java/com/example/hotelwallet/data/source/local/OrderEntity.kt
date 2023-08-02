package com.example.hotelwallet.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hotelwallet.data.model.PlanDto
import com.example.hotelwallet.data.model.SubMenuDto
import com.google.gson.annotations.SerializedName

@Entity
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerializedName("created_at")
    val createdAt: Long,
    @SerializedName("total_price")
    val totalPrice: Double,
    val category: Int,
    @SerializedName("plat_list")
    val platList: List<SubMenuDto> = emptyList(),
    @SerializedName("plan_list")
    val planList: List<PlanDto> = emptyList()
)
