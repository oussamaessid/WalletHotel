package com.example.hotelwallet.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.hotelwallet.domain.repository.AppDataStore
import com.example.hotelwallet.utility.APP_DATASTORE
import kotlinx.coroutines.flow.first

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = APP_DATASTORE)

class AppDataStoreManager(private val context: Context) : AppDataStore {

    override suspend fun saveStringValue(key: String, value: String) {
        val wrappedKey = stringPreferencesKey(key)
        context.datastore.edit {
            it[wrappedKey] = value
        }
    }

    override suspend fun saveDoubleValue(key: String, value: Double) {
        val wrappedKey = doublePreferencesKey(key)
        context.datastore.edit {
            it[wrappedKey] = value
        }
    }

    override suspend fun saveIntValue(key: String, value: Int) {
        val wrappedKey = intPreferencesKey(key)
        context.datastore.edit {
            it[wrappedKey] = value
        }
    }

    override suspend fun saveLongValue(key: String, value: Long) {
        val wrappedKey = longPreferencesKey(key)
        context.datastore.edit {
            it[wrappedKey] = value
        }
    }

    override suspend fun saveBooleanValue(key: String, value: Boolean) {
        val wrappedKey = booleanPreferencesKey(key)
        context.datastore.edit {
            it[wrappedKey] = value
        }
    }

    override suspend fun getStringValue(key: String): String? {
        val preferencesKey = stringPreferencesKey(key)
        val preferences = context.datastore.data.first()
        return preferences[preferencesKey]
    }

    override suspend fun getDoubleValue(key: String): Double? {
        val preferencesKey = doublePreferencesKey(key)
        val preferences = context.datastore.data.first()
        return preferences[preferencesKey]
    }

    override suspend fun getLongValue(key: String): Long? {
        val preferencesKey = longPreferencesKey(key)
        val preferences = context.datastore.data.first()
        return preferences[preferencesKey]
    }

    override suspend fun getBooleanValue(key: String): Boolean {
        val preferencesKey = booleanPreferencesKey(key)
        val preferences = context.datastore.data.first()
        return preferences[preferencesKey] ?: false
    }

    override suspend fun getIntValue(key: String): Int? {
        val preferencesKey = intPreferencesKey(key)
        val preferences = context.datastore.data.first()
        return preferences[preferencesKey]
    }

    override suspend fun deleteAllPreferences() {
        context.datastore.edit { preferences ->
            preferences.clear()
        }
    }
}