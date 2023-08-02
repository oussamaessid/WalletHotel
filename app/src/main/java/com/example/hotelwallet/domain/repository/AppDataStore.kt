package com.example.hotelwallet.domain.repository

interface AppDataStore {

    suspend fun saveStringValue(key: String, value: String)

    suspend fun getStringValue(key: String):String?

    suspend fun saveDoubleValue(key: String, value: Double)

    suspend fun getDoubleValue(key: String):Double?

    suspend fun saveLongValue(key: String, value: Long)

    suspend fun getLongValue(key: String):Long?

    suspend fun saveBooleanValue(key: String, value: Boolean)

    suspend fun getBooleanValue(key: String):Boolean

    suspend fun saveIntValue(key: String, value: Int)

    suspend fun getIntValue(key: String):Int?

    suspend fun deleteAllPreferences()
}