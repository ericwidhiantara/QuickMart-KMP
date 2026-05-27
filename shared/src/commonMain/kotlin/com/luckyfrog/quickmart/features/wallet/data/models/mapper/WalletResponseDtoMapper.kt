package com.luckyfrog.quickmart.features.wallet.data.models.mapper

import com.luckyfrog.quickmart.features.wallet.data.models.response.WalletResponseDto
import com.luckyfrog.quickmart.features.wallet.domain.entities.WalletEntity

fun WalletResponseDto.toEntity() = WalletEntity(
    id = id,
    userId = userId,
    balance = balance,
    localizedBalance = localizedBalance,
    createdAt = createdAt,
    updatedAt = updatedAt
)
