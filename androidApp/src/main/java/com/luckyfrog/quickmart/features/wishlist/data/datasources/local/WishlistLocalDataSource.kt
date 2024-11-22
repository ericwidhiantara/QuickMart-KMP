package com.luckyfrog.quickmart.features.wishlist.data.datasources.local

import com.luckyfrog.quickmart.features.wishlist.data.model.WishlistLocalItemDto


interface WishlistLocalDataSource {
    suspend fun insertItem(params: WishlistLocalItemDto)
    suspend fun deleteItem(params: WishlistLocalItemDto)
    suspend fun getAllItems(userId: String): List<WishlistLocalItemDto>
}