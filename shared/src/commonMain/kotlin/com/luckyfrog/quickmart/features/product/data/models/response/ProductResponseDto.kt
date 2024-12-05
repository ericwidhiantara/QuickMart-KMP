package com.luckyfrog.quickmart.features.product.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponseDto(
    val id: String?,
    @SerialName("created_at")
    val createdAt: Int?,
    @SerialName("updated_at")
    val updatedAt: Int?,
    @SerialName("created_by")
    val createdBy: String?,
    @SerialName("updated_by")
    val updatedBy: String?,
    @SerialName("category_id")
    val categoryId: String?,
    val name: String?,
    val price: Double?,
    @SerialName("localized_price")
    val localizedPrice: String?,
    val brand: String?,
    val image: String?,
    val description: String?,
    val tags: List<String>?,
    @SerialName("images")
    val images: List<String>?,
    val variants: List<VariantResponseDto>?
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
    val image: String?,
    val sku: String?,
    val price: Double?,
    @SerialName("discount_percentage")
    val discountPercentage: Double?,
    val weight: Int?,
    val dimensions: DimensionsResponseDto?,
    val stock: Int?,
    val size: String?,
    val model: String?,
    val color: String?,
    @SerialName("localized_price")
    val localizedPrice: String?
)

@Serializable
data class DimensionsResponseDto(
    val depth: Double?,
    val width: Double?,
    val height: Double?
)