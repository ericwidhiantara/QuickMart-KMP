package com.luckyfrog.quickmart.features.cart.data.repositories

import com.luckyfrog.quickmart.features.cart.data.datasources.local.CartLocalDataSource
import com.luckyfrog.quickmart.features.cart.data.model.CartLocalItemDto
import com.luckyfrog.quickmart.features.cart.domain.repositories.CartLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CartLocalRepositoryImpl @Inject constructor(private val localDataSource: CartLocalDataSource) :
    CartLocalRepository {
    override suspend fun insertItem(cartItem: CartLocalItemDto) =
        localDataSource.insertItem(cartItem)

    override suspend fun updateItem(cartItem: CartLocalItemDto) =
        localDataSource.updateItem(cartItem)

    override suspend fun deleteItem(cartItem: CartLocalItemDto) =
        localDataSource.deleteItem(cartItem)

    override suspend fun getAllItems(): Flow<List<CartLocalItemDto>> =
        flow { emit(localDataSource.getAllItems()) }

    override suspend fun getSelectedItems(): Flow<List<CartLocalItemDto>> =
        flow { emit(localDataSource.getSelectedItems()) }

    override suspend fun calculateSubtotal(): Flow<Double> =
        flow { emit(localDataSource.calculateSubtotal()) }
}
