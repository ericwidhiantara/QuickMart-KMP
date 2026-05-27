package com.luckyfrog.quickmart.features.order.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderResponseDto(
    val id: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    val status: String? = null,
    @SerialName("total_price")
    val totalPrice: Double? = null,
    @SerialName("localized_total_price")
    val localizedTotalPrice: String? = null,
    val items: List<OrderItemResponseDto>? = null
)

@Serializable
data class OrderItemResponseDto(
    val id: String? = null,
    @SerialName("product_id")
    val productId: String? = null,
    @SerialName("product_variant_id")
    val productVariantId: String? = null,
    @SerialName("product_name")
    val productName: String? = null,
    val quantity: Int? = null,
    val price: Double? = null,
    @SerialName("discount_percentage")
    val discountPercentage: Double? = null,
    @SerialName("final_price")
    val finalPrice: Double? = null
)

@Serializable
data class CheckoutRequestDto(
    @SerialName("cart_item_ids")
    val cartItemIds: List<String>? = null
)
