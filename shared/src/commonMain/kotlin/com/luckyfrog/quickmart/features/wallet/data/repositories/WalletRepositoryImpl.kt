package com.luckyfrog.quickmart.features.wallet.data.repositories

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.network.processResponse
import com.luckyfrog.quickmart.features.wallet.data.datasources.remote.WalletRemoteDataSource
import com.luckyfrog.quickmart.features.wallet.data.models.mapper.toEntity
import com.luckyfrog.quickmart.features.wallet.domain.entities.WalletEntity
import com.luckyfrog.quickmart.features.wallet.domain.repositories.WalletRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WalletRepositoryImpl(
    private val remoteDataSource: WalletRemoteDataSource
) : WalletRepository {

    override suspend fun getWallet(): Flow<ApiResponse<ResponseDto<WalletEntity>>> = flow {
        emit(ApiResponse.Loading)
        val response = remoteDataSource.getWallet()
        emit(processResponse(response) { it.data?.toEntity() })
    }

    override suspend fun topUp(amount: Double): Flow<ApiResponse<ResponseDto<WalletEntity>>> = flow {
        emit(ApiResponse.Loading)
        val response = remoteDataSource.topUp(amount)
        emit(processResponse(response) { it.data?.toEntity() })
    }
}
