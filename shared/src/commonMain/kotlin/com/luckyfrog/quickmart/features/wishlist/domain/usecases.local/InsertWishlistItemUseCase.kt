package com.luckyfrog.quickmart.features.wishlist.domain.usecases.local

import com.luckyfrog.quickmart.features.wishlist.data.models.WishlistLocalItemDto
import com.luckyfrog.quickmart.features.wishlist.domain.repositories.WishlistLocalRepository

class InsertWishlistItemUseCase(private val repository: WishlistLocalRepository) {
    suspend operator fun invoke(item: WishlistLocalItemDto) = repository.insertItem(item)
}