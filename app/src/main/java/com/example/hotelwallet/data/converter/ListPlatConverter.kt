package com.example.hotelwallet.data.converter

import androidx.room.TypeConverter
import com.example.hotelwallet.data.model.SubMenuDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListPlatConverter {

    @TypeConverter
    fun fromStringToList(values: String?): List<SubMenuDto>? = values?.let {
        Gson().fromJson(
            values,
            object : TypeToken<List<SubMenuDto>?>() {}.type
        )
    }

    @TypeConverter
    fun fromListToString(values: List<SubMenuDto>?): String {
        val gson = Gson()
        return gson.toJson(values)
    }

}