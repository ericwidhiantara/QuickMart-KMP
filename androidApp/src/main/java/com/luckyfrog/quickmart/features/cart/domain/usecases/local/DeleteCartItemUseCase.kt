package com.luckyfrog.quickmart.features.cart.domain.usecases.local

import com.luckyfrog.quickmart.features.cart.data.model.CartLocalItemDto
import com.luckyfrog.quickmart.features.cart.domain.repositories.CartLocalRepository
import javax.inject.Inject

class DeleteCartItemUseCase @Inject constructor(private val repository: CartLocalRepository) {
    suspend operator fun invoke(cartItem: CartLocalItemDto) = repository.deleteItem(cartItem)
}