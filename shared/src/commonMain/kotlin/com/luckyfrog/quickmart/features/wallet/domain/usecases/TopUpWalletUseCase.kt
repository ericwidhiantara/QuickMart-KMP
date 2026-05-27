package com.luckyfrog.quickmart.features.wallet.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.wallet.domain.entities.TopUpParamsEntity
import com.luckyfrog.quickmart.features.wallet.domain.entities.WalletEntity
import com.luckyfrog.quickmart.features.wallet.domain.repositories.WalletRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import com.luckyfrog.quickmart.utils.UseCase
import kotlinx.coroutines.flow.Flow

class TopUpWalletUseCase(private val repository: WalletRepository) :
    UseCase<TopUpParamsEntity, Flow<ApiResponse<ResponseDto<WalletEntity>>>> {
    override suspend fun execute(params: TopUpParamsEntity) = repository.topUp(params.amount)
}
