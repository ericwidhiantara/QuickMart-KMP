package com.luckyfrog.quickmart.features.wallet.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.wallet.data.models.response.WalletResponseDto

interface WalletRemoteDataSource {
    suspend fun getWallet(): ResponseDto<WalletResponseDto>
    suspend fun topUp(amount: Double): ResponseDto<WalletResponseDto>
}

class WalletRemoteDataSourceImpl(private val api: WalletApi) : WalletRemoteDataSource {
    override suspend fun getWallet(): ResponseDto<WalletResponseDto> = api.getWallet()
    override suspend fun topUp(amount: Double): ResponseDto<WalletResponseDto> = api.topUp(amount)
}
