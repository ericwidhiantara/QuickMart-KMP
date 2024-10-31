package com.luckyfrog.quickmart.features.product.data.models.mapper

import com.luckyfrog.quickmart.features.product.data.models.response.DimensionsResponseDto
import com.luckyfrog.quickmart.features.product.data.models.response.MetaResponseDto
import com.luckyfrog.quickmart.features.product.data.models.response.ProductResponseDto
import com.luckyfrog.quickmart.features.product.data.models.response.ReviewResponseDto
import com.luckyfrog.quickmart.features.product.domain.entities.DimensionsEntity
import com.luckyfrog.quickmart.features.product.domain.entities.MetaEntity
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import com.luckyfrog.quickmart.features.product.domain.entities.ReviewEntity

// Mapping from DTO to Entity
fun ProductResponseDto.toEntity() = ProductEntity(
    id = this.id,
    title = this.title,
    price = this.price,
    description = this.description,
    category = this.category,
    thumbnail = this.thumbnail, // Assuming thumbnail is used as the image
    rating = this.rating,
    discountPercentage = this.discountPercentage,
    stock = this.stock,
    tags = this.tags,
    sku = this.sku,
    weight = this.weight,
    dimensions = this.dimensions.toEntity(),
    warrantyInformation = this.warrantyInformation,
    shippingInformation = this.shippingInformation,
    availabilityStatus = this.availabilityStatus,
    reviews = this.reviews.toEntity(),
    returnPolicy = this.returnPolicy,
    minimumOrderQuantity = this.minimumOrderQuantity,
    meta = this.meta.toEntity(),
    images = this.images,
    brand = this.brand
)

// Mapping from Entity to DTO
fun ProductEntity.toDto() = ProductResponseDto(
    id = this.id,
    title = this.title,
    price = this.price,
    description = this.description,
    category = this.category,
    thumbnail = this.thumbnail, // Assuming image is stored as thumbnail
    rating = this.rating,
    discountPercentage = this.discountPercentage,
    stock = this.stock,
    tags = this.tags,
    sku = this.sku,
    weight = this.weight,
    dimensions = this.dimensions.toDto(),
    warrantyInformation = this.warrantyInformation,
    shippingInformation = this.shippingInformation,
    availabilityStatus = this.availabilityStatus,
    reviews = this.reviews.toDto(),
    returnPolicy = this.returnPolicy,
    minimumOrderQuantity = this.minimumOrderQuantity,
    meta = this.meta.toDto(),
    images = this.images,
    brand = this.brand
)

fun DimensionsResponseDto.toEntity() = DimensionsEntity(width, height, depth)
fun DimensionsEntity.toDto() = DimensionsResponseDto(width, height, depth)

fun MetaResponseDto.toEntity() = MetaEntity(createdAt, updatedAt, barcode, qrCode)
fun MetaEntity.toDto() = MetaResponseDto(createdAt, updatedAt, barcode, qrCode)

fun List<ReviewResponseDto>.toEntity() =
    this.map { ReviewEntity(it.rating, it.comment, it.date, it.reviewerName, it.reviewerEmail) }

fun List<ReviewEntity>.toDto() = this.map {
    ReviewResponseDto(
        it.rating,
        it.comment,
        it.date,
        it.reviewerName,
        it.reviewerEmail
    )
}


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