package com.luckyfrog.quickmart.features.order.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.PaginationDto
import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.order.data.models.response.CheckoutRequestDto
import com.luckyfrog.quickmart.features.order.data.models.response.OrderResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface OrderApi {
    suspend fun checkout(
        body: CheckoutRequestDto
    ): ResponseDto<OrderResponseDto>

    suspend fun getOrders(
        status: String?,
        page: Int,
        limit: Int
    ): ResponseDto<PaginationDto<OrderResponseDto>>

    suspend fun getOrderDetail(
        id: String
    ): ResponseDto<OrderResponseDto>

    suspend fun cancelOrder(
        id: String
    ): ResponseDto<OrderResponseDto>
}

class OrderApiImpl(private val client: HttpClient) : OrderApi {

    override suspend fun checkout(body: CheckoutRequestDto): ResponseDto<OrderResponseDto> {
        val response = client.post("orders") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        return response.body()
    }

    override suspend fun getOrders(
        status: String?,
        page: Int,
        limit: Int
    ): ResponseDto<PaginationDto<OrderResponseDto>> {
        val response = client.get("orders") {
            if (status != null) parameter("status", status)
            parameter("page", page)
            parameter("limit", limit)
        }
        return response.body()
    }

    override suspend fun getOrderDetail(id: String): ResponseDto<OrderResponseDto> {
        val response = client.get("orders/$id")
        return response.body()
    }

    override suspend fun cancelOrder(id: String): ResponseDto<OrderResponseDto> {
        val response = client.patch("orders/$id/cancel")
        return response.body()
    }
}
