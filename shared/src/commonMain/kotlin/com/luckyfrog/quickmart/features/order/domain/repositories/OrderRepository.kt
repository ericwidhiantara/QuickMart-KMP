package com.luckyfrog.quickmart.features.order.domain.repositories

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.entities.PaginationEntity
import com.luckyfrog.quickmart.features.order.domain.entities.CheckoutParamsEntity
import com.luckyfrog.quickmart.features.order.domain.entities.GetOrdersParamsEntity
import com.luckyfrog.quickmart.features.order.domain.entities.OrderEntity
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun checkout(params: CheckoutParamsEntity): Flow<ApiResponse<ResponseDto<OrderEntity>>>
    suspend fun getOrders(params: GetOrdersParamsEntity): Flow<ApiResponse<ResponseDto<PaginationEntity<OrderEntity>>>>
    suspend fun getOrderDetail(id: String): Flow<ApiResponse<ResponseDto<OrderEntity>>>
    suspend fun cancelOrder(id: String): Flow<ApiResponse<ResponseDto<OrderEntity>>>
}
