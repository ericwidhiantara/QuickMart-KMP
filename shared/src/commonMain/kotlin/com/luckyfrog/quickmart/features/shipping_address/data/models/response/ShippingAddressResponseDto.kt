package com.luckyfrog.quickmart.features.shipping_address.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShippingAddressResponseDto(
    val id: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    @SerialName("user_id")
    val userId: String? = null,
    val label: String? = null,
    @SerialName("recipient_name")
    val recipientName: String? = null,
    val phone: String? = null,
    @SerialName("address_line")
    val addressLine: String? = null,
    val city: String? = null,
    val province: String? = null,
    @SerialName("postal_code")
    val postalCode: String? = null,
    val country: String? = null,
    @SerialName("is_default")
    val isDefault: Boolean = false
)

@Serializable
data class CreateShippingAddressRequestDto(
    val label: String,
    @SerialName("recipient_name")
    val recipientName: String,
    val phone: String,
    @SerialName("address_line")
    val addressLine: String,
    val city: String,
    val province: String,
    @SerialName("postal_code")
    val postalCode: String,
    val country: String = "Indonesia",
    @SerialName("is_default")
    val isDefault: Boolean = false
)

@Serializable
data class UpdateShippingAddressRequestDto(
    val label: String? = null,
    @SerialName("recipient_name")
    val recipientName: String? = null,
    val phone: String? = null,
    @SerialName("address_line")
    val addressLine: String? = null,
    val city: String? = null,
    val province: String? = null,
    @SerialName("postal_code")
    val postalCode: String? = null,
    val country: String? = null,
    @SerialName("is_default")
    val isDefault: Boolean? = null
)
