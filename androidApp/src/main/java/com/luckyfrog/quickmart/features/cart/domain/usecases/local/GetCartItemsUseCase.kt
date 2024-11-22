package com.luckyfrog.quickmart.features.cart.domain.usecases.local

import com.luckyfrog.quickmart.features.cart.domain.repositories.CartLocalRepository
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(private val repository: CartLocalRepository) {
    suspend operator fun invoke(userId: String) = repository.getAllItems(userId)
}