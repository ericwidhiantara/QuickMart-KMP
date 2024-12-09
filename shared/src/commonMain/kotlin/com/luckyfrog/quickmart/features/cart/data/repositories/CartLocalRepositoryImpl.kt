package com.luckyfrog.quickmart.features.cart.data.repositories

import com.luckyfrog.quickmart.features.cart.data.datasources.local.CartLocalDataSource
import com.luckyfrog.quickmart.features.cart.data.model.CartLocalItemDto
import com.luckyfrog.quickmart.features.cart.domain.repositories.CartLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CartLocalRepositoryImpl(private val localDataSource: CartLocalDataSource) :
    CartLocalRepository {
    override suspend fun insertItem(cartItem: CartLocalItemDto) =
        localDataSource.insertItem(cartItem)

    override suspend fun updateItem(cartItem: CartLocalItemDto) =
        localDataSource.updateItem(cartItem)

    override suspend fun deleteItem(cartItem: CartLocalItemDto) =
        localDataSource.deleteItem(cartItem)

    override suspend fun getAllItems(userId: String): Flow<List<CartLocalItemDto>> =
        flow { emit(localDataSource.getAllItems(userId)) }

    override suspend fun getSelectedItems(userId: String): Flow<List<CartLocalItemDto>> =
        flow { emit(localDataSource.getSelectedItems(userId)) }

    override suspend fun calculateSubtotal(userId: String): Flow<Double> =
        flow { emit(localDataSource.calculateSubtotal(userId)) }
}
