package com.luckyfrog.quickmart.features.product.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.product.data.models.response.ProductResponseDto

interface ProductRemoteDataSource {

    suspend fun getProducts(
        limit: Int,
        skip: Int
    ): ResponseDto<List<ProductResponseDto>>


    suspend fun getProductDetail(
        id: Int
    ): ProductResponseDto

}
