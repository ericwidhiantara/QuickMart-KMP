package com.luckyfrog.quickmart.features.wishlist.data.models

data class WishlistLocalItemDto(
    val id: Long? = null,
    val userId: String,
    val productId: String,
    val productName: String,
    val productImage: String,
    val productPrice: Double,
    val discountPercentage: Double = 0.0,
)