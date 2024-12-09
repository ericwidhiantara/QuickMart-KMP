package com.luckyfrog.quickmart.features.wishlist.domain.usecases.local

import com.luckyfrog.quickmart.features.wishlist.domain.repositories.WishlistLocalRepository

class GetWishlistItemsUseCase(private val repository: WishlistLocalRepository) {
    suspend operator fun invoke(userId: String) = repository.getAllItems(userId)
}