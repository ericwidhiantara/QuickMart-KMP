package com.luckyfrog.quickmart.features.cart.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luckyfrog.quickmart.features.cart.data.datasources.local.dao.CartDao
import com.luckyfrog.quickmart.features.cart.data.model.CartLocalItemDto

@Database(
    entities = [CartLocalItemDto::class],
    version = 1,
    exportSchema = false
)
abstract class CartAppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}