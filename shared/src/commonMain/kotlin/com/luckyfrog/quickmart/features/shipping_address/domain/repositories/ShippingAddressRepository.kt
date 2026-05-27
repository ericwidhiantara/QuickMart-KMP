package com.luckyfrog.quickmart.features.shipping_address.domain.repositories

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.shipping_address.domain.entities.CreateShippingAddressParamsEntity
import com.luckyfrog.quickmart.features.shipping_address.domain.entities.ShippingAddressEntity
import com.luckyfrog.quickmart.features.shipping_address.domain.entities.UpdateShippingAddressParamsEntity
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface ShippingAddressRepository {
    suspend fun createAddress(params: CreateShippingAddressParamsEntity): Flow<ApiResponse<ResponseDto<ShippingAddressEntity>>>
    suspend fun getMyAddresses(): Flow<ApiResponse<ResponseDto<List<ShippingAddressEntity>>>>
    suspend fun getAddressById(id: String): Flow<ApiResponse<ResponseDto<ShippingAddressEntity>>>
    suspend fun updateAddress(params: UpdateShippingAddressParamsEntity): Flow<ApiResponse<ResponseDto<ShippingAddressEntity>>>
    suspend fun setDefault(id: String): Flow<ApiResponse<ResponseDto<ShippingAddressEntity>>>
    suspend fun deleteAddress(id: String): Flow<ApiResponse<ResponseDto<Unit>>>
}
