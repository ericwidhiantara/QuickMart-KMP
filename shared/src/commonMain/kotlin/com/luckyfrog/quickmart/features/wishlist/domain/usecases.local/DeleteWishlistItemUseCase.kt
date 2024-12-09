package com.luckyfrog.quickmart.features.wishlist.domain.usecases.local

import com.luckyfrog.quickmart.features.wishlist.data.models.WishlistLocalItemDto
import com.luckyfrog.quickmart.features.wishlist.domain.repositories.WishlistLocalRepository

class DeleteWishlistItemUseCase(private val repository: WishlistLocalRepository) {
    suspend operator fun invoke(item: WishlistLocalItemDto) = repository.deleteItem(item)
}