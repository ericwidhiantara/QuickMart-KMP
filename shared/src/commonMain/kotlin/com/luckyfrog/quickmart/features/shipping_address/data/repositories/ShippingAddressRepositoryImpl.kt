package com.luckyfrog.quickmart.features.shipping_address.data.repositories

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.network.processResponse
import com.luckyfrog.quickmart.features.shipping_address.data.datasources.remote.ShippingAddressRemoteDataSource
import com.luckyfrog.quickmart.features.shipping_address.data.models.mapper.toEntity
import com.luckyfrog.quickmart.features.shipping_address.data.models.mapper.toEntityList
import com.luckyfrog.quickmart.features.shipping_address.domain.entities.CreateShippingAddressParamsEntity
import com.luckyfrog.quickmart.features.shipping_address.domain.entities.ShippingAddressEntity
import com.luckyfrog.quickmart.features.shipping_address.domain.entities.UpdateShippingAddressParamsEntity
import com.luckyfrog.quickmart.features.shipping_address.domain.repositories.ShippingAddressRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ShippingAddressRepositoryImpl(
    private val remoteDataSource: ShippingAddressRemoteDataSource
) : ShippingAddressRepository {

    override suspend fun createAddress(params: CreateShippingAddressParamsEntity): Flow<ApiResponse<ResponseDto<ShippingAddressEntity>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.createAddress(params = params)
            emit(processResponse(response) { it.data?.toEntity() })
        }

    override suspend fun getMyAddresses(): Flow<ApiResponse<ResponseDto<List<ShippingAddressEntity>>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.getMyAddresses()
            emit(processResponse(response) { it.data?.toEntityList() })
        }

    override suspend fun getAddressById(id: String): Flow<ApiResponse<ResponseDto<ShippingAddressEntity>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.getAddressById(id = id)
            emit(processResponse(response) { it.data?.toEntity() })
        }

    override suspend fun updateAddress(params: UpdateShippingAddressParamsEntity): Flow<ApiResponse<ResponseDto<ShippingAddressEntity>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.updateAddress(params = params)
            emit(processResponse(response) { it.data?.toEntity() })
        }

    override suspend fun setDefault(id: String): Flow<ApiResponse<ResponseDto<ShippingAddressEntity>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.setDefault(id = id)
            emit(processResponse(response) { it.data?.toEntity() })
        }

    override suspend fun deleteAddress(id: String): Flow<ApiResponse<ResponseDto<Unit>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.deleteAddress(id = id)
            emit(processResponse(response) { Unit })
        }
}
