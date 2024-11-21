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

    @Query("SELECT * FROM cart_items")
    suspend fun getAllItems(): List<CartLocalItemDto>

    @Query("SELECT * FROM cart_items WHERE selected = 1")
    suspend fun getSelectedItems(): List<CartLocalItemDto>

    @Query("SELECT SUM((productPrice - (productPrice * discountPercentage / 100)) * qty) FROM cart_items WHERE selected = 1")
    suspend fun calculateSubtotal(): Double
}
