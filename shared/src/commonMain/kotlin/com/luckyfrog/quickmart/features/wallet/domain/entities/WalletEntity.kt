package com.luckyfrog.quickmart.features.wallet.domain.entities

data class WalletEntity(
    val id: String,
    val userId: String,
    val balance: Double,
    val localizedBalance: String,
    val createdAt: String,
    val updatedAt: String
)

data class TopUpParamsEntity(val amount: Double)
