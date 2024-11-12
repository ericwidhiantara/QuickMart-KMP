package com.luckyfrog.quickmart.features.product.data.models.mapper

import com.luckyfrog.quickmart.features.product.data.models.response.DimensionsResponseDto
import com.luckyfrog.quickmart.features.product.data.models.response.ProductResponseDto
import com.luckyfrog.quickmart.features.product.domain.entities.DimensionsEntity
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity

// Mapping from DTO to Entity
fun ProductResponseDto.toEntity() = ProductEntity(
    id = this.id,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    createdBy = this.createdBy,
    updatedBy = this.updatedBy,
    categoryId = this.categoryId,
    sku = this.sku,
    name = this.name,
    stock = this.stock,
    price = this.price,
    localizedPrice = this.localizedPrice,
    tags = this.tags,
    description = this.description,
    discountPercentage = this.discountPercentage,
    brand = this.brand,
    weight = this.weight,
    dimensions = this.dimensions?.toEntity(),
    img = this.img
)

// Mapping from Entity to DTO
fun ProductEntity.toDto() = ProductResponseDto(
    id = this.id,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    createdBy = this.createdBy,
    updatedBy = this.updatedBy,
    categoryId = this.categoryId,
    sku = this.sku,
    name = this.name,
    stock = this.stock,
    price = this.price,
    localizedPrice = this.localizedPrice,
    tags = this.tags,
    description = this.description,
    discountPercentage = this.discountPercentage,
    brand = this.brand,
    weight = this.weight,
    dimensions = this.dimensions?.toDto(),
    img = this.img
)

fun DimensionsResponseDto.toEntity() = DimensionsEntity(width, height, depth)
fun DimensionsEntity.toDto() = DimensionsResponseDto(width, height, depth)


fun List<ProductResponseDto>.toEntityList(): List<ProductEntity> {
    return this.map {
        it.toEntity()
    }
}

fun List<ProductEntity>.toDtoList(): List<ProductResponseDto> {
    return this.map {
        it.toDto()
    }
}