package com.luckyfrog.quickmart.features.wishlist.domain.repositories


import com.luckyfrog.quickmart.features.wishlist.data.models.WishlistLocalItemDto
import kotlinx.coroutines.flow.Flow

interface WishlistLocalRepository {
    suspend fun insertItem(item: WishlistLocalItemDto)
    suspend fun deleteItem(item: WishlistLocalItemDto)
    suspend fun getAllItems(userId: String): Flow<List<WishlistLocalItemDto>>
}
