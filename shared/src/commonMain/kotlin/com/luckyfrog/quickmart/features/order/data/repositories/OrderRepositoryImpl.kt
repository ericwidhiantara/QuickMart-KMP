package com.luckyfrog.quickmart.features.order.data.repositories

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.entities.PaginationEntity
import com.luckyfrog.quickmart.core.generic.mapper.toEntity
import com.luckyfrog.quickmart.core.network.processResponse
import com.luckyfrog.quickmart.features.order.data.datasources.remote.OrderRemoteDataSource
import com.luckyfrog.quickmart.features.order.data.models.mapper.toEntity
import com.luckyfrog.quickmart.features.order.domain.entities.CheckoutParamsEntity
import com.luckyfrog.quickmart.features.order.domain.entities.GetOrdersParamsEntity
import com.luckyfrog.quickmart.features.order.domain.entities.OrderEntity
import com.luckyfrog.quickmart.features.order.domain.repositories.OrderRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OrderRepositoryImpl(
    private val remoteDataSource: OrderRemoteDataSource
) : OrderRepository {

    override suspend fun checkout(params: CheckoutParamsEntity): Flow<ApiResponse<ResponseDto<OrderEntity>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.checkout(params = params)
            emit(processResponse(response) { it.data?.toEntity() })
        }

    override suspend fun getOrders(params: GetOrdersParamsEntity): Flow<ApiResponse<ResponseDto<PaginationEntity<OrderEntity>>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.getOrders(params = params)
            emit(processResponse(response) { it ->
                it.data?.toEntity(mapper = { it.toEntity() })
            })
        }

    override suspend fun getOrderDetail(id: String): Flow<ApiResponse<ResponseDto<OrderEntity>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.getOrderDetail(id = id)
            emit(processResponse(response) { it.data?.toEntity() })
        }

    override suspend fun cancelOrder(id: String): Flow<ApiResponse<ResponseDto<OrderEntity>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.cancelOrder(id = id)
            emit(processResponse(response) { it.data?.toEntity() })
        }
}
