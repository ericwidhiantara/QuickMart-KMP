package com.luckyfrog.quickmart.features.cart.domain.repositories


import com.luckyfrog.quickmart.features.cart.data.model.CartLocalItemDto
import kotlinx.coroutines.flow.Flow

interface CartLocalRepository {
    suspend fun insertItem(cartItem: CartLocalItemDto)
    suspend fun updateItem(cartItem: CartLocalItemDto)
    suspend fun deleteItem(cartItem: CartLocalItemDto)
    suspend fun getAllItems(userId: String): Flow<List<CartLocalItemDto>>
    suspend fun getSelectedItems(userId: String): Flow<List<CartLocalItemDto>>
    suspend fun calculateSubtotal(userId: String): Flow<Double>
}
