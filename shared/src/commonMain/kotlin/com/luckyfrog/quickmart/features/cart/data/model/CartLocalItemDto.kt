package com.luckyfrog.quickmart.features.cart.data.model

data class CartLocalItemDto(
    val id: Long? = null,
    val userId: String,
    val productId: String,
    val productName: String,
    val productImage: String,
    val productPrice: Double,
    val discountPercentage: Double = 0.0,
    val qty: Int,
    val selected: Boolean = false
)