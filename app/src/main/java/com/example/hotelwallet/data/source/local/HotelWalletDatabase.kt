package com.example.hotelwallet.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hotelwallet.data.converter.ListPlanConverter
import com.example.hotelwallet.data.converter.ListPlatConverter
import com.example.hotelwallet.data.model.FavoriteEntity
import com.example.hotelwallet.data.model.SubMenuDto
import com.example.hotelwallet.data.source.local.dao.FavoriteDao
import com.example.hotelwallet.data.source.local.dao.OrderDao
import com.example.hotelwallet.data.source.local.dao.ProductDao

@Database(entities = [SubMenuDto::class, FavoriteEntity::class, OrderEntity::class], version = 1)
@TypeConverters(ListPlatConverter::class, ListPlanConverter::class)
abstract class HotelWalletDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun orderDao(): OrderDao

    companion object {
        const val DATABASE_NAME = "hotel_wallet"
    }
}