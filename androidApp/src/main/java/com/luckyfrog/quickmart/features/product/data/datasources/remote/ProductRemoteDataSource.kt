package com.luckyfrog.quickmart.features.product.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.product.data.models.response.ProductResponseDto
import com.luckyfrog.quickmart.features.product.domain.entities.ProductFormParamsEntity

interface ProductRemoteDataSource {

    suspend fun getProducts(
        params: ProductFormParamsEntity
    ): ResponseDto<List<ProductResponseDto>>

    suspend fun getProductsByCategory(
        params: ProductFormParamsEntity
    ): ResponseDto<List<ProductResponseDto>>


    suspend fun getProductDetail(
        id: Int
    ): ProductResponseDto

}
