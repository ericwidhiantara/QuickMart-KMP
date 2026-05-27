package com.luckyfrog.quickmart.features.wallet.domain.repositories

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.wallet.domain.entities.WalletEntity
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface WalletRepository {
    suspend fun getWallet(): Flow<ApiResponse<ResponseDto<WalletEntity>>>
    suspend fun topUp(amount: Double): Flow<ApiResponse<ResponseDto<WalletEntity>>>
}
