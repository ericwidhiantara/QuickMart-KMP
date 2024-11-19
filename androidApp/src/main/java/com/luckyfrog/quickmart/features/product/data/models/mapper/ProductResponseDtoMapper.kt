package com.luckyfrog.quickmart.features.product.data.models.mapper

import com.luckyfrog.quickmart.features.product.data.models.response.DimensionsResponseDto
import com.luckyfrog.quickmart.features.product.data.models.response.ProductResponseDto
import com.luckyfrog.quickmart.features.product.data.models.response.VariantResponseDto
import com.luckyfrog.quickmart.features.product.domain.entities.DimensionsEntity
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import com.luckyfrog.quickmart.features.product.domain.entities.VariantEntity

// Mapping from DTO to Entity
fun ProductResponseDto.toEntity() = ProductEntity(
    id = id,
    createdAt = createdAt,
    updatedAt = updatedAt,
    createdBy = createdBy,
    updatedBy = updatedBy,
    categoryId = categoryId,
    name = name,
    price = price,
    localizedPrice = localizedPrice,
    brand = brand,
    image = image,
    description = description,
    tags = tags,
    images = images,
    variants = variants?.map { it.toEntity() }
)

fun VariantResponseDto.toEntity() = VariantEntity(
    id = id,
    createdAt = createdAt,
    updatedAt = updatedAt,
    createdBy = createdBy,
    updatedBy = updatedBy,
    isMain = isMain,
    productId = productId,
    image = image,
    sku = sku,
    price = price,
    discountPercentage = discountPercentage,
    weight = weight,
    dimensions = dimensions?.toEntity(),
    stock = stock,
    size = size,
    model = model,
    color = color,
    localizedPrice = localizedPrice
)

fun DimensionsResponseDto.toEntity() = DimensionsEntity(
    width = width,
    height = height,
    depth = depth
)

// Mapping from Entity to DTO
fun ProductEntity.toDto() = ProductResponseDto(
    id = id,
    createdAt = createdAt,
    updatedAt = updatedAt,
    createdBy = createdBy,
    updatedBy = updatedBy,
    categoryId = categoryId,
    name = name,
    price = price,
    localizedPrice = localizedPrice,
    brand = brand,
    image = image,
    description = description,
    tags = tags,
    images = images,
    variants = variants?.map { it.toDto() }
)

fun VariantEntity.toDto() = VariantResponseDto(
    id = id,
    createdAt = createdAt,
    updatedAt = updatedAt,
    createdBy = createdBy,
    updatedBy = updatedBy,
    isMain = isMain,
    productId = productId,
    image = image,
    sku = sku,
    price = price,
    discountPercentage = discountPercentage,
    weight = weight,
    dimensions = dimensions?.toDto(),
    stock = stock,
    size = size,
    model = model,
    color = color,
    localizedPrice = localizedPrice
)

fun DimensionsEntity.toDto() = DimensionsResponseDto(
    width = width,
    height = height,
    depth = depth
)

// List mappings
fun List<ProductResponseDto>.toEntityList(): List<ProductEntity> = map { it.toEntity() }
fun List<ProductEntity>.toDtoList(): List<ProductResponseDto> = map { it.toDto() }
