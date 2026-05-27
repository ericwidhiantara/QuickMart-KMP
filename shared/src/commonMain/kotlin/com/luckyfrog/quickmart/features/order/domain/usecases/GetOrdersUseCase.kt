package com.luckyfrog.quickmart.features.order.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.entities.PaginationEntity
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.order.domain.entities.GetOrdersParamsEntity
import com.luckyfrog.quickmart.features.order.domain.entities.OrderEntity
import com.luckyfrog.quickmart.features.order.domain.repositories.OrderRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class GetOrdersUseCase(
    private val repository: OrderRepository
) : UseCase<GetOrdersParamsEntity, Flow<ApiResponse<ResponseDto<PaginationEntity<OrderEntity>>>>> {
    override suspend fun execute(input: GetOrdersParamsEntity): Flow<ApiResponse<ResponseDto<PaginationEntity<OrderEntity>>>> {
        return repository.getOrders(input)
    }
}
