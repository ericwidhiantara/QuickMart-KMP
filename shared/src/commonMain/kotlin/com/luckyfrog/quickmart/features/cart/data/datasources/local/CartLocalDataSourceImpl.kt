package com.luckyfrog.quickmart.features.cart.data.datasources.local

import com.luckyfrog.quickmart.features.cart.data.model.CartLocalItemDto

class CartLocalDataSourceImpl(
    private val database: CartAppDatabase
) : CartLocalDataSource {
    override suspend fun insertItem(cartItem: CartLocalItemDto) {
        database.dbQuery?.insertItem(
            userId = cartItem.userId,
            productId = cartItem.productId,
            productName = cartItem.productName,
            productImage = cartItem.productImage,
            productPrice = cartItem.productPrice,
            discountPercentage = cartItem.discountPercentage,
            qty = cartItem.qty.toLong(),
            selected = if (cartItem.selected) 1L else 0L
        )
    }

    override suspend fun updateItem(cartItem: CartLocalItemDto) {
        database.dbQuery?.updateItem(
            productId = cartItem.productId,
            productName = cartItem.productName,
            productImage = cartItem.productImage,
            productPrice = cartItem.productPrice,
            discountPercentage = cartItem.discountPercentage,
            qty = cartItem.qty.toLong(),
            selected = if (cartItem.selected) 1L else 0L,
            id = cartItem.id
                ?: throw IllegalArgumentException("Item ID must not be null for update")
        )
    }

    override suspend fun deleteItem(cartItem: CartLocalItemDto) {
        cartItem.id?.let { database.dbQuery?.deleteItem(it) }
            ?: throw IllegalArgumentException("Item ID must not be null for deletion")
    }

    override suspend fun getAllItems(userId: String): List<CartLocalItemDto> {
        return database.dbQuery?.getAllItems(userId)
            ?.executeAsList()
            ?.map { query ->
                CartLocalItemDto(
                    id = query.id,
                    userId = query.userId,
                    productId = query.productId,
                    productName = query.productName,
                    productImage = query.productImage,
                    productPrice = query.productPrice,
                    discountPercentage = query.discountPercentage,
                    qty = query.qty.toInt(),
                    selected = query.selected == 1L
                )
            } ?: emptyList()
    }

    override suspend fun getSelectedItems(userId: String): List<CartLocalItemDto> {
        return database.dbQuery?.getSelectedItems(userId)?.executeAsList()
            ?.map { query ->
                CartLocalItemDto(
                    id = query.id,
                    userId = query.userId,
                    productId = query.productId,
                    productName = query.productName,
                    productImage = query.productImage,
                    productPrice = query.productPrice,
                    discountPercentage = query.discountPercentage,
                    qty = query.qty.toInt(),
                    selected = query.selected == 1L
                )
            } ?: emptyList()
    }

    override suspend fun calculateSubtotal(userId: String): Double {
        return database.dbQuery?.calculateSubtotal(userId)?.executeAsOne() ?: 0.0
    }
}