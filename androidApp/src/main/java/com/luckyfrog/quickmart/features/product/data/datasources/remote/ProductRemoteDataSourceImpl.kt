package com.luckyfrog.quickmart.features.product.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.product.data.models.response.ProductResponseDto
import com.luckyfrog.quickmart.features.product.domain.entities.ProductFormParamsEntity
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val api: ProductApi
) : ProductRemoteDataSource {

    override suspend fun getProducts(
        params: ProductFormParamsEntity
    ): ResponseDto<List<ProductResponseDto>> {
        return api.getProducts(
            limit = params.limit,
            skip = params.skip,
            order = params.order,
            sortBy = params.sortBy,
        )
    }

    override suspend fun getProductsByCategory(
        params: ProductFormParamsEntity
    ): ResponseDto<List<ProductResponseDto>> {
        return api.getProductsByCategory(
            limit = params.limit,
            skip = params.skip,
            category = params.category ?: "",
            order = params.order,
            sortBy = params.sortBy,
        )
    }

    override suspend fun getProductDetail(id: Int): ProductResponseDto {
        return api.getProductDetail(id = id)
    }
}