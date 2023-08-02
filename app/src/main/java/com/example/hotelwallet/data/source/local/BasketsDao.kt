package com.example.hotelwallet.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BasketsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(basket: Basket)
//    suspend fun addFavorite(basket: Basket)

    @Delete
    suspend fun deleteFavorite(basket: Basket)

    @Query("SELECT * FROM favorite_table")
    fun getAllFavorites(): LiveData<List<Basket>>

//    @Query("SELECT * FROM favorite_table")
//    fun getFavorites(): LiveData<List<Basket>>
//
//    @Query("SELECT * FROM favorite_table WHERE name =:name")
//    fun getMealById(name: String): Basket
//
//    @Query("DELETE FROM favorite_table WHERE name =:name")
//    fun deleteMealById(name: String)
//
//    @Delete
//    fun deleteMeal(basket: Basket)
}
