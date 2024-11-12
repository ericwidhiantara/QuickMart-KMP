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
    val sku: String?,
    val name: String?,
    val stock: Int?,
    val price: Double?,
    @SerializedName("localized_price")
    val localizedPrice: String?,
    val tags: List<String>?,
    val description: String?,
    @SerializedName("discount_percentage")
    val discountPercentage: Double?,
    val brand: String?,
    val weight: Int?,
    val dimensions: DimensionsResponseDto?,
    val img: List<String>? = null
)

data class DimensionsResponseDto(
    @SerializedName("width")
    val width: Double?,
    @SerializedName("height")
    val height: Double?,
    @SerializedName("depth")
    val depth: Double?
)
