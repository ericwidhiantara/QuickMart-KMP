package com.luckyfrog.quickmart.features.product.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.PaginationDto
import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.product.data.models.response.ProductResponseDto
import com.luckyfrog.quickmart.features.product.domain.entities.ProductFormParamsEntity
import retrofit2.Response

interface ProductRemoteDataSource {

    suspend fun getProducts(
        params: ProductFormParamsEntity
    ): Response<ResponseDto<PaginationDto<ProductResponseDto>>>

    suspend fun getProductDetail(
        id: String
    ): Response<ResponseDto<ProductResponseDto>>

}
