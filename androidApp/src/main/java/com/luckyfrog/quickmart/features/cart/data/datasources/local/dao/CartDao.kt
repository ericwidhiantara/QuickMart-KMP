package com.luckyfrog.quickmart.features.cart.data.datasources.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.luckyfrog.quickmart.features.cart.data.model.CartLocalItemDto

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(cartItem: CartLocalItemDto)

    @Update
    suspend fun updateItem(cartItem: CartLocalItemDto)

    @Delete
    suspend fun deleteItem(cartItem: CartLocalItemDto)

    @Query("SELECT * FROM cart_items WHERE userId = :userId")
    suspend fun getAllItems(userId: String): List<CartLocalItemDto>

    @Query("SELECT * FROM cart_items WHERE selected = 1 AND userId = :userId")
    suspend fun getSelectedItems(userId: String): List<CartLocalItemDto>

    @Query("SELECT SUM((productPrice - (productPrice * discountPercentage / 100)) * qty) FROM cart_items WHERE selected = 1 AND userId = :userId")
    suspend fun calculateSubtotal(userId: String): Double
}
