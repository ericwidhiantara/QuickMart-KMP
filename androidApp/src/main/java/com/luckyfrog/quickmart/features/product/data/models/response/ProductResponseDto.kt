package com.luckyfrog.quickmart.features.product.data.models.response

import com.google.gson.annotations.SerializedName

data class ProductResponseDto(
    val id: String?,
    @SerializedName("created_at")
    val createdAt: Int?,
    @SerializedName("updated_at")
    val updatedAt: Int?,
    @SerializedName("created_by")
    val createdBy: String?,
    @SerializedName("updated_by")
    val updatedBy: String?,
    @SerializedName("category_id")
    val categoryId: String?,
    val name: String?,
    val brand: String?,
    val description: String?,
    val tags: List<String>?,
    @SerializedName("images")
    val images: List<String>?,
    val variants: List<VariantResponseDto>?
)

data class VariantResponseDto(
    val id: String?,
    @SerializedName("created_at")
    val createdAt: Int?,
    @SerializedName("updated_at")
    val updatedAt: Int?,
    @SerializedName("created_by")
    val createdBy: String?,
    @SerializedName("updated_by")
    val updatedBy: String?,
    @SerializedName("is_main")
    val isMain: Boolean?,
    @SerializedName("product_id")
    val productId: String?,
    val image: String?,
    val sku: String?,
    val price: Double?,
    @SerializedName("discount_percentage")
    val discountPercentage: Double?,
    val weight: Int?,
    val dimensions: DimensionsResponseDto?,
    val stock: Int?,
    val size: String?,
    val model: String?,
    val color: String?,
    @SerializedName("localized_price")
    val localizedPrice: String?
)

data class DimensionsResponseDto(
    val depth: Double?,
    val width: Double?,
    val height: Double?
)