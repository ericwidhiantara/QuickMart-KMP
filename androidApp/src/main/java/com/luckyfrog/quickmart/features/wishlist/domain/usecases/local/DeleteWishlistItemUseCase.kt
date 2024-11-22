package com.luckyfrog.quickmart.features.wishlist.domain.usecases.local

import com.luckyfrog.quickmart.features.wishlist.data.model.WishlistLocalItemDto
import com.luckyfrog.quickmart.features.wishlist.domain.repositories.WishlistLocalRepository
import javax.inject.Inject

class DeleteWishlistItemUseCase @Inject constructor(private val repository: WishlistLocalRepository) {
    suspend operator fun invoke(params: WishlistLocalItemDto) = repository.deleteItem(params)
}