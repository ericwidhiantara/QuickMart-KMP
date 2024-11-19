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
    val name: String?,
    val price: Double?,
    val localizedPrice: String?,
    val brand: String?,
    val image: String?,
    val description: String?,
    val tags: List<String>?,
    val images: List<String>?,
    val variants: List<VariantEntity>?
)

@Serializable
data class VariantEntity(
    val id: String?,
    val createdAt: Int?,
    val updatedAt: Int?,
    val createdBy: String?,
    val updatedBy: String?,
    val isMain: Boolean?,
    val productId: String?,
    val image: String?,
    val sku: String?,
    val price: Double?,
    val discountPercentage: Double?,
    val weight: Int?,
    val dimensions: DimensionsEntity?,
    val stock: Int?,
    val size: String?,
    val model: String?,
    val color: String?,
    val localizedPrice: String?
)

@Serializable
data class DimensionsEntity(
    val depth: Double?,
    val width: Double?,
    val height: Double?
)