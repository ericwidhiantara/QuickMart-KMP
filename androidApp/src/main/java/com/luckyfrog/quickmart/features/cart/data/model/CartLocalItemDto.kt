package com.luckyfrog.quickmart.features.cart.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartLocalItemDto(
    @PrimaryKey val id: String,
    val productImage: String,
    val productName: String,
    val productPrice: Double,
    val discountPercentage: Double,
    val qty: Int,
    val selected: Boolean
)
