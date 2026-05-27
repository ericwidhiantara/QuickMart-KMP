package com.luckyfrog.quickmart.features.shipping_address.data.models.mapper

import com.luckyfrog.quickmart.features.shipping_address.data.models.response.ShippingAddressResponseDto
import com.luckyfrog.quickmart.features.shipping_address.domain.entities.ShippingAddressEntity

fun ShippingAddressResponseDto.toEntity() = ShippingAddressEntity(
    id = id,
    createdAt = createdAt,
    updatedAt = updatedAt,
    userId = userId,
    label = label,
    recipientName = recipientName,
    phone = phone,
    addressLine = addressLine,
    city = city,
    province = province,
    postalCode = postalCode,
    country = country,
    isDefault = isDefault
)

fun List<ShippingAddressResponseDto>.toEntityList(): List<ShippingAddressEntity> = map { it.toEntity() }
