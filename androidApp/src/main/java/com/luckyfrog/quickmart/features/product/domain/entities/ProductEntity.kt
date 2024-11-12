package com.luckyfrog.quickmart.features.product.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class ProductEntity(
    val id: String?,
    val createdAt: Int?,
    val updatedAt: Int?,
    val createdBy: String?,
    val updatedBy: String?,
    val categoryId: String?,
    val sku: String?,
    val name: String?,
    val stock: Int?,
    val price: Double?,
    val localizedPrice: String?,
    val tags: List<String>?,
    val description: String?,
    val discountPercentage: Double?,
    val brand: String?,
    val weight: Int?,
    val dimensions: DimensionsEntity?,
    val img: List<String>? = null
)

@Serializable
data class DimensionsEntity(
    val width: Double?,
    val height: Double?,
    val depth: Double?
)
