package com.example.hotelwallet.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(basket: Favorite)


    @Delete
    suspend fun deleteFavorite(basket: Favorite)


    @Query("SELECT * FROM favorite_table2")
    fun getFavorites(): LiveData<List<Favorite>>

    @Query("SELECT * FROM favorite_table2 WHERE name =:name")
    fun getMealById(name: String): Favorite

    @Query("DELETE FROM favorite_table2 WHERE name =:name")
    fun deleteMealById(name: String)

    @Delete
    fun deleteMeal(basket: Favorite)
}
