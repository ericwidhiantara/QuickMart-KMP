package com.luckyfrog.quickmart.features.wishlist.data.datasources.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luckyfrog.quickmart.features.wishlist.data.model.WishlistLocalItemDto

@Dao
interface WishlistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(params: WishlistLocalItemDto)

    @Delete
    suspend fun deleteItem(params: WishlistLocalItemDto)

    @Query("SELECT * FROM wishlist_items")
    suspend fun getAllItems(): List<WishlistLocalItemDto>
}
