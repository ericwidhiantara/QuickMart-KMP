package com.luckyfrog.quickmart.features.wishlist.data.datasources.local

import com.luckyfrog.quickmart.features.wishlist.data.datasources.local.dao.WishlistDao
import com.luckyfrog.quickmart.features.wishlist.data.model.WishlistLocalItemDto
import javax.inject.Inject


class WishlistLocalDataSourceImpl @Inject constructor(private val dao: WishlistDao) :
    WishlistLocalDataSource {
    override suspend fun insertItem(params: WishlistLocalItemDto) = dao.insertItem(params)
    override suspend fun deleteItem(params: WishlistLocalItemDto) = dao.deleteItem(params)
    override suspend fun getAllItems(userId: String) = dao.getAllItems(userId)

}