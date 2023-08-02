package com.example.hotelwallet.data.source.local.dao

import androidx.room.*
import com.example.hotelwallet.data.model.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: FavoriteEntity)

    @Delete
    suspend fun deleteProduct(product: FavoriteEntity)

    @Query("SELECT * FROM FavoriteEntity")
    fun getAllFavorites(): List<FavoriteEntity>?
}
