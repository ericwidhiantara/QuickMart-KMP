package com.luckyfrog.quickmart.features.wishlist.domain.usecases.local

import com.luckyfrog.quickmart.features.wishlist.domain.repositories.WishlistLocalRepository
import javax.inject.Inject

class GetWishlistItemsUseCase @Inject constructor(private val repository: WishlistLocalRepository) {
    suspend operator fun invoke(userId: String) = repository.getAllItems(userId)
}