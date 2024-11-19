package com.luckyfrog.quickmart.features.product.data.repositories

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.entities.PaginationEntity
import com.luckyfrog.quickmart.core.generic.mapper.toEntity
import com.luckyfrog.quickmart.core.network.processResponse
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductRemoteDataSource
import com.luckyfrog.quickmart.features.product.data.models.mapper.toEntity
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import com.luckyfrog.quickmart.features.product.domain.entities.ProductFormParamsEntity
import com.luckyfrog.quickmart.features.product.domain.repositories.ProductRepository
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource
) : ProductRepository {

    override suspend fun getProducts(params: ProductFormParamsEntity): Flow<ApiResponse<ResponseDto<PaginationEntity<ProductEntity>>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.getProducts(params = params)
            emit(processResponse(response) { it ->
                it.data?.toEntity(
                    mapper = { it.toEntity() }
                )
            })
        }

    override suspend fun getProductDetail(id: String): Flow<ApiResponse<ResponseDto<ProductEntity>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.getProductDetail(id = id)
            emit(processResponse(response) { it ->
                it.data?.toEntity()
            })
        }

}
