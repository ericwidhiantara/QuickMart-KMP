package com.luckyfrog.quickmart.features.cart.domain.usecases.local

import com.luckyfrog.quickmart.features.cart.domain.repositories.CartLocalRepository

class CalculateSubtotalUseCase(private val repository: CartLocalRepository) {
    suspend operator fun invoke(userId: String) = repository.calculateSubtotal(userId)
}