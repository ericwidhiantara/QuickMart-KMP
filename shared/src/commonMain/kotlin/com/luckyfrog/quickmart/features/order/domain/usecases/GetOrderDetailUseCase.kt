package com.luckyfrog.quickmart.features.order.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.order.domain.entities.OrderEntity
import com.luckyfrog.quickmart.features.order.domain.repositories.OrderRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class GetOrderDetailUseCase(
    private val repository: OrderRepository
) : UseCase<String, Flow<ApiResponse<ResponseDto<OrderEntity>>>> {
    override suspend fun execute(input: String): Flow<ApiResponse<ResponseDto<OrderEntity>>> {
        return repository.getOrderDetail(input)
    }
}
