package com.luckyfrog.quickmart.features.cart.data.datasources.local

import com.luckyfrog.quickmart.features.cart.data.model.CartLocalItemDto


interface CartLocalDataSource {
    suspend fun insertItem(cartItem: CartLocalItemDto)
    suspend fun updateItem(cartItem: CartLocalItemDto)
    suspend fun deleteItem(cartItem: CartLocalItemDto)
    suspend fun getAllItems(): List<CartLocalItemDto>
    suspend fun getSelectedItems(): List<CartLocalItemDto>
    suspend fun calculateSubtotal(): Double
}