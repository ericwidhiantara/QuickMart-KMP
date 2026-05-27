package com.luckyfrog.quickmart.features.shipping_address.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class ShippingAddressEntity(
    val id: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val userId: String?,
    val label: String?,
    val recipientName: String?,
    val phone: String?,
    val addressLine: String?,
    val city: String?,
    val province: String?,
    val postalCode: String?,
    val country: String?,
    val isDefault: Boolean
)

@Serializable
data class CreateShippingAddressParamsEntity(
    val label: String,
    val recipientName: String,
    val phone: String,
    val addressLine: String,
    val city: String,
    val province: String,
    val postalCode: String,
    val country: String = "Indonesia",
    val isDefault: Boolean = false
)

@Serializable
data class UpdateShippingAddressParamsEntity(
    val id: String,
    val label: String? = null,
    val recipientName: String? = null,
    val phone: String? = null,
    val addressLine: String? = null,
    val city: String? = null,
    val province: String? = null,
    val postalCode: String? = null,
    val country: String? = null,
    val isDefault: Boolean? = null
)
