package com.luckyfrog.quickmart.features.product.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponseDto(
    val id: String?,
    @SerialName("created_at")
    val createdAt: Int? = null,
    @SerialName("updated_at")
    val updatedAt: Int? = null,
    @SerialName("created_by")
    val createdBy: String? = null,
    @SerialName("updated_by")
    val updatedBy: String? = null,
    @SerialName("category_id")
    val categoryId: String? = null,
    val name: String?,
    val price: Double? = null,
    @SerialName("localized_price")
    val localizedPrice: String? = null,
    val brand: String? = null,
    val image: String? = null,
    val description: String? = null,
    val tags: List<String>? = null,
    @SerialName("images")
    val images: List<String>? = null,
    val variants: List<VariantResponseDto>? = null
)

@Serializable
data class VariantResponseDto(
    val id: String?,
    @SerialName("created_at")
    val createdAt: Int?,
    @SerialName("updated_at")
    val updatedAt: Int?,
    @SerialName("created_by")
    val createdBy: String?,
    @SerialName("updated_by")
    val updatedBy: String?,
    @SerialName("is_main")
    val isMain: Boolean?,
    @SerialName("product_id")
    val productId: String?,
    val image: String? = null,
    val sku: String?,
    val price: Double? = null,
    @SerialName("discount_percentage")
    val discountPercentage: Double?,
    val weight: Double?,
    val dimensions: DimensionsResponseDto?,
    val stock: Int?,
    val size: String? = null,
    val model: String? = null,
    val color: String? = null,
    @SerialName("localized_price")
    val localizedPrice: String? = null
)

@Serializable
data class DimensionsResponseDto(
    val depth: Double?,
    val width: Double?,
    val height: Double?
)