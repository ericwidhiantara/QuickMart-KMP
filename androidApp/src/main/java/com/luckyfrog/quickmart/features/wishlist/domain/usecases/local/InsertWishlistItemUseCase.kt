package com.luckyfrog.quickmart.features.wishlist.domain.usecases.local

import com.luckyfrog.quickmart.features.wishlist.data.model.WishlistLocalItemDto
import com.luckyfrog.quickmart.features.wishlist.domain.repositories.WishlistLocalRepository
import javax.inject.Inject

class InsertWishlistItemUseCase @Inject constructor(private val repository: WishlistLocalRepository) {
    suspend operator fun invoke(params: WishlistLocalItemDto) = repository.insertItem(params)
}