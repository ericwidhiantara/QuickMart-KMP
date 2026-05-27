package com.luckyfrog.quickmart.features.shipping_address.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.shipping_address.data.models.response.CreateShippingAddressRequestDto
import com.luckyfrog.quickmart.features.shipping_address.data.models.response.ShippingAddressResponseDto
import com.luckyfrog.quickmart.features.shipping_address.data.models.response.UpdateShippingAddressRequestDto
import com.luckyfrog.quickmart.features.shipping_address.domain.entities.CreateShippingAddressParamsEntity
import com.luckyfrog.quickmart.features.shipping_address.domain.entities.UpdateShippingAddressParamsEntity

interface ShippingAddressRemoteDataSource {
    suspend fun createAddress(params: CreateShippingAddressParamsEntity): ResponseDto<ShippingAddressResponseDto>
    suspend fun getMyAddresses(): ResponseDto<List<ShippingAddressResponseDto>>
    suspend fun getAddressById(id: String): ResponseDto<ShippingAddressResponseDto>
    suspend fun updateAddress(params: UpdateShippingAddressParamsEntity): ResponseDto<ShippingAddressResponseDto>
    suspend fun setDefault(id: String): ResponseDto<ShippingAddressResponseDto>
    suspend fun deleteAddress(id: String): ResponseDto<Unit>
}

class ShippingAddressRemoteDataSourceImpl(
    private val api: ShippingAddressApi
) : ShippingAddressRemoteDataSource {

    override suspend fun createAddress(params: CreateShippingAddressParamsEntity): ResponseDto<ShippingAddressResponseDto> {
        return api.createAddress(
            body = CreateShippingAddressRequestDto(
                label = params.label,
                recipientName = params.recipientName,
                phone = params.phone,
                addressLine = params.addressLine,
                city = params.city,
                province = params.province,
                postalCode = params.postalCode,
                country = params.country,
                isDefault = params.isDefault
            )
        )
    }

    override suspend fun getMyAddresses(): ResponseDto<List<ShippingAddressResponseDto>> {
        return api.getMyAddresses()
    }

    override suspend fun getAddressById(id: String): ResponseDto<ShippingAddressResponseDto> {
        return api.getAddressById(id = id)
    }

    override suspend fun updateAddress(params: UpdateShippingAddressParamsEntity): ResponseDto<ShippingAddressResponseDto> {
        return api.updateAddress(
            id = params.id,
            body = UpdateShippingAddressRequestDto(
                label = params.label,
                recipientName = params.recipientName,
                phone = params.phone,
                addressLine = params.addressLine,
                city = params.city,
                province = params.province,
                postalCode = params.postalCode,
                country = params.country,
                isDefault = params.isDefault
            )
        )
    }

    override suspend fun setDefault(id: String): ResponseDto<ShippingAddressResponseDto> {
        return api.setDefault(id = id)
    }

    override suspend fun deleteAddress(id: String): ResponseDto<Unit> {
        return api.deleteAddress(id = id)
    }
}
