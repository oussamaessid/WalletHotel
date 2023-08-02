package com.example.hotelwallet.data.converter

import androidx.room.TypeConverter
import com.example.hotelwallet.data.model.PlanDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListPlanConverter {

    @TypeConverter
    fun fromStringToList(values: String?): List<PlanDto>? = values?.let {
        Gson().fromJson(
            values,
            object : TypeToken<List<PlanDto>?>() {}.type
        )
    }

    @TypeConverter
    fun fromListToString(values: List<PlanDto>?): String {
        val gson = Gson()
        return gson.toJson(values)
    }

}