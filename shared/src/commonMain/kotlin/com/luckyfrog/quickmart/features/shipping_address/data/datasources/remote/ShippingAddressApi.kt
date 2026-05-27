package com.luckyfrog.quickmart.features.shipping_address.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.shipping_address.data.models.response.CreateShippingAddressRequestDto
import com.luckyfrog.quickmart.features.shipping_address.data.models.response.ShippingAddressResponseDto
import com.luckyfrog.quickmart.features.shipping_address.data.models.response.UpdateShippingAddressRequestDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface ShippingAddressApi {
    suspend fun createAddress(body: CreateShippingAddressRequestDto): ResponseDto<ShippingAddressResponseDto>
    suspend fun getMyAddresses(): ResponseDto<List<ShippingAddressResponseDto>>
    suspend fun getAddressById(id: String): ResponseDto<ShippingAddressResponseDto>
    suspend fun updateAddress(id: String, body: UpdateShippingAddressRequestDto): ResponseDto<ShippingAddressResponseDto>
    suspend fun setDefault(id: String): ResponseDto<ShippingAddressResponseDto>
    suspend fun deleteAddress(id: String): ResponseDto<Unit>
}

class ShippingAddressApiImpl(private val client: HttpClient) : ShippingAddressApi {

    override suspend fun createAddress(body: CreateShippingAddressRequestDto): ResponseDto<ShippingAddressResponseDto> {
        val response = client.post("shipping-addresses") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        return response.body()
    }

    override suspend fun getMyAddresses(): ResponseDto<List<ShippingAddressResponseDto>> {
        val response = client.get("shipping-addresses")
        return response.body()
    }

    override suspend fun getAddressById(id: String): ResponseDto<ShippingAddressResponseDto> {
        val response = client.get("shipping-addresses/$id")
        return response.body()
    }

    override suspend fun updateAddress(id: String, body: UpdateShippingAddressRequestDto): ResponseDto<ShippingAddressResponseDto> {
        val response = client.put("shipping-addresses/$id") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        return response.body()
    }

    override suspend fun setDefault(id: String): ResponseDto<ShippingAddressResponseDto> {
        val response = client.patch("shipping-addresses/$id/set-default")
        return response.body()
    }

    override suspend fun deleteAddress(id: String): ResponseDto<Unit> {
        val response = client.delete("shipping-addresses/$id")
        return response.body()
    }
}
