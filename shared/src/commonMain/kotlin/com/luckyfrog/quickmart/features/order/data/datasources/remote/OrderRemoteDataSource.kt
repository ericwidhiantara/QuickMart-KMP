package com.luckyfrog.quickmart.features.order.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.PaginationDto
import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.order.data.models.response.CheckoutRequestDto
import com.luckyfrog.quickmart.features.order.data.models.response.OrderResponseDto
import com.luckyfrog.quickmart.features.order.domain.entities.CheckoutParamsEntity
import com.luckyfrog.quickmart.features.order.domain.entities.GetOrdersParamsEntity

interface OrderRemoteDataSource {
    suspend fun checkout(params: CheckoutParamsEntity): ResponseDto<OrderResponseDto>
    suspend fun getOrders(params: GetOrdersParamsEntity): ResponseDto<PaginationDto<OrderResponseDto>>
    suspend fun getOrderDetail(id: String): ResponseDto<OrderResponseDto>
    suspend fun cancelOrder(id: String): ResponseDto<OrderResponseDto>
}

class OrderRemoteDataSourceImpl(
    private val api: OrderApi
) : OrderRemoteDataSource {

    override suspend fun checkout(params: CheckoutParamsEntity): ResponseDto<OrderResponseDto> {
        return api.checkout(
            body = CheckoutRequestDto(cartItemIds = params.cartItemIds)
        )
    }

    override suspend fun getOrders(params: GetOrdersParamsEntity): ResponseDto<PaginationDto<OrderResponseDto>> {
        return api.getOrders(
            status = params.status,
            page = params.page,
            limit = params.limit
        )
    }

    override suspend fun getOrderDetail(id: String): ResponseDto<OrderResponseDto> {
        return api.getOrderDetail(id = id)
    }

    override suspend fun cancelOrder(id: String): ResponseDto<OrderResponseDto> {
        return api.cancelOrder(id = id)
    }
}
