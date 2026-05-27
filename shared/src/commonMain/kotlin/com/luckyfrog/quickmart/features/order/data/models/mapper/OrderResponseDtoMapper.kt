package com.luckyfrog.quickmart.features.order.data.models.mapper

import com.luckyfrog.quickmart.features.order.data.models.response.OrderItemResponseDto
import com.luckyfrog.quickmart.features.order.data.models.response.OrderResponseDto
import com.luckyfrog.quickmart.features.order.domain.entities.OrderEntity
import com.luckyfrog.quickmart.features.order.domain.entities.OrderItemEntity

fun OrderResponseDto.toEntity() = OrderEntity(
    id = id,
    createdAt = createdAt,
    updatedAt = updatedAt,
    status = status,
    totalPrice = totalPrice,
    localizedTotalPrice = localizedTotalPrice,
    items = items?.map { it.toEntity() }
)

fun OrderItemResponseDto.toEntity() = OrderItemEntity(
    id = id,
    productId = productId,
    productVariantId = productVariantId,
    productName = productName,
    quantity = quantity,
    price = price,
    discountPercentage = discountPercentage,
    finalPrice = finalPrice
)

fun List<OrderResponseDto>.toEntityList(): List<OrderEntity> = map { it.toEntity() }
