package com.luckyfrog.quickmart.features.cart.domain.usecases.local

import com.luckyfrog.quickmart.features.cart.domain.repositories.CartLocalRepository

class GetSelectedCartItemsUseCase(private val repository: CartLocalRepository) {
    suspend operator fun invoke(userId: String) = repository.getSelectedItems(userId)
}