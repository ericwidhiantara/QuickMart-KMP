package com.luckyfrog.quickmart.features.product.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.product.data.models.response.ProductResponseDto
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val api: ProductApi
) : ProductRemoteDataSource {

    override suspend fun getProducts(
        limit: Int,
        skip: Int
    ): ResponseDto<List<ProductResponseDto>> {
        return api.getProducts(limit = limit, skip = skip)
    }


    override suspend fun getProductDetail(id: Int): ProductResponseDto {
        return api.getProductDetail(id = id)
    }
}