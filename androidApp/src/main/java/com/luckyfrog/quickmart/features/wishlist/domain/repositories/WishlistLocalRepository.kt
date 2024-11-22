package com.luckyfrog.quickmart.features.wishlist.domain.repositories


import com.luckyfrog.quickmart.features.wishlist.data.model.WishlistLocalItemDto
import kotlinx.coroutines.flow.Flow

interface WishlistLocalRepository {
    suspend fun insertItem(params: WishlistLocalItemDto)
    suspend fun deleteItem(params: WishlistLocalItemDto)
    suspend fun getAllItems(userId: String): Flow<List<WishlistLocalItemDto>>
}
