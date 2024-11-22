package com.luckyfrog.quickmart.features.wishlist.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luckyfrog.quickmart.features.wishlist.data.datasources.local.dao.WishlistDao
import com.luckyfrog.quickmart.features.wishlist.data.model.WishlistLocalItemDto

@Database(
    entities = [WishlistLocalItemDto::class],
    version = 1,
    exportSchema = false
)
abstract class WishlistAppDatabase : RoomDatabase() {
    abstract fun wishlistDao(): WishlistDao
}