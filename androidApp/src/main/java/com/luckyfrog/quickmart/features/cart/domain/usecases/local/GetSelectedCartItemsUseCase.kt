package com.luckyfrog.quickmart.features.cart.domain.usecases.local

import com.luckyfrog.quickmart.features.cart.domain.repositories.CartLocalRepository
import javax.inject.Inject

class GetSelectedCartItemsUseCase @Inject constructor(private val repository: CartLocalRepository) {
    suspend operator fun invoke(userId: String) = repository.getSelectedItems(userId)
}