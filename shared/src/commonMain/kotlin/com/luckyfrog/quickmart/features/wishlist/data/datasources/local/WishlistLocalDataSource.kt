package com.luckyfrog.quickmart.features.wishlist.data.datasources.local

import com.luckyfrog.quickmart.features.wishlist.data.models.WishlistLocalItemDto

interface WishlistLocalDataSource {
    suspend fun insertItem(item: WishlistLocalItemDto)
    suspend fun deleteItem(item: WishlistLocalItemDto)
    suspend fun getAllItems(userId: String): List<WishlistLocalItemDto>
}