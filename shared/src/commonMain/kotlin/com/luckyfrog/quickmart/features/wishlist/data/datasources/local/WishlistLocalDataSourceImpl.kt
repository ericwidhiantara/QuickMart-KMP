package com.luckyfrog.quickmart.features.wishlist.data.datasources.local

import com.luckyfrog.quickmart.features.wishlist.data.models.WishlistLocalItemDto

class WishlistLocalDataSourceImpl(
    private val database: WishlistAppDatabase
) : WishlistLocalDataSource {
    override suspend fun insertItem(item: WishlistLocalItemDto) {
        database.dbQuery?.insertItem(
            userId = item.userId,
            productId = item.productId,
            productName = item.productName,
            productImage = item.productImage,
            productPrice = item.productPrice,
            discountPercentage = item.discountPercentage,
        )
    }

    override suspend fun deleteItem(item: WishlistLocalItemDto) {
        item.id?.let { database.dbQuery?.deleteItem(it) }
            ?: throw IllegalArgumentException("Item ID must not be null for deletion")
    }

    override suspend fun getAllItems(userId: String): List<WishlistLocalItemDto> {
        return database.dbQuery?.getAllItems(userId)
            ?.executeAsList()
            ?.map { query ->
                WishlistLocalItemDto(
                    id = query.id,
                    userId = query.userId,
                    productId = query.productId,
                    productName = query.productName,
                    productImage = query.productImage,
                    productPrice = query.productPrice,
                    discountPercentage = query.discountPercentage,
                )
            } ?: emptyList()
    }

}