package com.luckyfrog.quickmart.features.cart.data.datasources.local

import com.luckyfrog.quickmart.features.cart.data.model.CartLocalItemDto


interface CartLocalDataSource {
    suspend fun insertItem(cartItem: CartLocalItemDto)
    suspend fun updateItem(cartItem: CartLocalItemDto)
    suspend fun deleteItem(cartItem: CartLocalItemDto)
    suspend fun getAllItems(userId: String): List<CartLocalItemDto>
    suspend fun getSelectedItems(userId: String): List<CartLocalItemDto>
    suspend fun calculateSubtotal(userId: String): Double
}