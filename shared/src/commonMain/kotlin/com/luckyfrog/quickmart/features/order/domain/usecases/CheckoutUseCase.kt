package com.luckyfrog.quickmart.features.order.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.order.domain.entities.CheckoutParamsEntity
import com.luckyfrog.quickmart.features.order.domain.entities.OrderEntity
import com.luckyfrog.quickmart.features.order.domain.repositories.OrderRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class CheckoutUseCase(
    private val repository: OrderRepository
) : UseCase<CheckoutParamsEntity, Flow<ApiResponse<ResponseDto<OrderEntity>>>> {
    override suspend fun execute(input: CheckoutParamsEntity): Flow<ApiResponse<ResponseDto<OrderEntity>>> {
        return repository.checkout(input)
    }
}
