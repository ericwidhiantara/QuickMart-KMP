package com.luckyfrog.quickmart.features.wishlist.data.repositories

import com.luckyfrog.quickmart.features.wishlist.data.datasources.local.WishlistLocalDataSource
import com.luckyfrog.quickmart.features.wishlist.data.models.WishlistLocalItemDto
import com.luckyfrog.quickmart.features.wishlist.domain.repositories.WishlistLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WishlistLocalRepositoryImpl(private val localDataSource: WishlistLocalDataSource) :
    WishlistLocalRepository {
    override suspend fun insertItem(item: WishlistLocalItemDto) =
        localDataSource.insertItem(item)

    override suspend fun deleteItem(item: WishlistLocalItemDto) =
        localDataSource.deleteItem(item)

    override suspend fun getAllItems(userId: String): Flow<List<WishlistLocalItemDto>> =
        flow { emit(localDataSource.getAllItems(userId)) }

}
