package com.luckyfrog.quickmart.features.wishlist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist_items")
data class WishlistLocalItemDto(
    @PrimaryKey val id: String,
    val userId: String,
    val productImage: String,
    val productName: String,
    val productPrice: Double,
    val discountPercentage: Double,
)
