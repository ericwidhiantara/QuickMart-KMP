package com.luckyfrog.quickmart.features.order.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class OrderEntity(
    val id: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val status: String?,
    val totalPrice: Double?,
    val localizedTotalPrice: String?,
    val items: List<OrderItemEntity>?
)

@Serializable
data class OrderItemEntity(
    val id: String?,
    val productId: String?,
    val productVariantId: String?,
    val productName: String?,
    val quantity: Int?,
    val price: Double?,
    val discountPercentage: Double?,
    val finalPrice: Double?
)

@Serializable
data class CheckoutParamsEntity(
    val cartItemIds: List<String>? = null
)

@Serializable
data class GetOrdersParamsEntity(
    val status: String? = null,
    val page: Int = 1,
    val limit: Int = 10
)
