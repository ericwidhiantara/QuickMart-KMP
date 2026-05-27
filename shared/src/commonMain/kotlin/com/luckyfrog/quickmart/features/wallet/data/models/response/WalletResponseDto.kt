package com.luckyfrog.quickmart.features.wallet.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalletResponseDto(
    @SerialName("id") val id: String,
    @SerialName("user_id") val userId: String,
    @SerialName("balance") val balance: Double,
    @SerialName("localized_balance") val localizedBalance: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
)

@Serializable
data class TopUpRequestDto(
    @SerialName("amount") val amount: Double
)
