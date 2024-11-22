package com.luckyfrog.quickmart.features.wishlist.data.repositories

import com.luckyfrog.quickmart.features.wishlist.data.datasources.local.WishlistLocalDataSource
import com.luckyfrog.quickmart.features.wishlist.data.model.WishlistLocalItemDto
import com.luckyfrog.quickmart.features.wishlist.domain.repositories.WishlistLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WishlistLocalRepositoryImpl @Inject constructor(private val localDataSource: WishlistLocalDataSource) :
    WishlistLocalRepository {
    override suspend fun insertItem(params: WishlistLocalItemDto) =
        localDataSource.insertItem(params)

    override suspend fun deleteItem(params: WishlistLocalItemDto) =
        localDataSource.deleteItem(params)

    override suspend fun getAllItems(): Flow<List<WishlistLocalItemDto>> =
        flow { emit(localDataSource.getAllItems()) }
}
