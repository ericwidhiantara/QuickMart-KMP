package com.luckyfrog.quickmart.features.wallet.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.wallet.data.models.response.TopUpRequestDto
import com.luckyfrog.quickmart.features.wallet.data.models.response.WalletResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

interface WalletApi {
    suspend fun getWallet(): ResponseDto<WalletResponseDto>
    suspend fun topUp(amount: Double): ResponseDto<WalletResponseDto>
}

class WalletApiImpl(private val client: HttpClient) : WalletApi {
    override suspend fun getWallet(): ResponseDto<WalletResponseDto> =
        client.get("wallet").body()

    override suspend fun topUp(amount: Double): ResponseDto<WalletResponseDto> =
        client.post("wallet/topup") {
            setBody(TopUpRequestDto(amount = amount))
        }.body()
}
