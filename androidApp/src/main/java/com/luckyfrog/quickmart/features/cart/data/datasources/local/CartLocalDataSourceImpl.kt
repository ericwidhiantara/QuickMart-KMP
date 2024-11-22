package com.luckyfrog.quickmart.features.cart.data.datasources.local

import com.luckyfrog.quickmart.features.cart.data.datasources.local.dao.CartDao
import com.luckyfrog.quickmart.features.cart.data.model.CartLocalItemDto
import javax.inject.Inject


class CartLocalDataSourceImpl @Inject constructor(private val cartDao: CartDao) :
    CartLocalDataSource {
    override suspend fun insertItem(cartItem: CartLocalItemDto) = cartDao.insertItem(cartItem)
    override suspend fun updateItem(cartItem: CartLocalItemDto) = cartDao.updateItem(cartItem)
    override suspend fun deleteItem(cartItem: CartLocalItemDto) = cartDao.deleteItem(cartItem)
    override suspend fun getAllItems(userId: String) = cartDao.getAllItems(userId)
    override suspend fun getSelectedItems(userId: String) = cartDao.getSelectedItems(userId)
    override suspend fun calculateSubtotal(userId: String) = cartDao.calculateSubtotal(userId)
}