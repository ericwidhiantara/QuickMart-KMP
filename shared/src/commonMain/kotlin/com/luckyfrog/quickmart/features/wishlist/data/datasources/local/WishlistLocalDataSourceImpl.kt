package com.luckyfrog.quickmart.features.wishlist.data.datasources.local

import com.luckyfrog.quickmart.features.wishlist.data.models.WishlistLocalItemDto

class WishlistLocalDataSourceImpl(
    private val database: WishlistAppDatabase
) : WishlistLocalDataSource {
    override suspend fun insertItem(item: WishlistLocalItemDto) {
        database.invoke { db ->
            db.wishlistQueries.addWishlist(
                userId = item.userId,
                productId = item.productId,
                productName = item.productName,
                productImage = item.productImage,
                productPrice = item.productPrice,
                discountPercentage = item.discountPercentage,
            )
        }
        println("Item inserted: $item")
    }

    override suspend fun deleteItem(item: WishlistLocalItemDto) {
        database.invoke { db ->
            item.productId.let {
                db.wishlistQueries.deleteWishlist(it)
            }
        }
    }

    override suspend fun getAllItems(userId: String): List<WishlistLocalItemDto> {
        return database.invoke { db ->
            db.wishlistQueries.getAllWishlist(userId)
                .executeAsList()
                .map { query ->
                    WishlistLocalItemDto(
                        id = query.id,
                        userId = query.userId,
                        productId = query.productId,
                        productName = query.productName,
                        productImage = query.productImage,
                        productPrice = query.productPrice,
                        discountPercentage = query.discountPercentage,
                    )
                }
        }
    }
}