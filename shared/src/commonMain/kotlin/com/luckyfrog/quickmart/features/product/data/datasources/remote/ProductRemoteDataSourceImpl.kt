package com.luckyfrog.quickmart.features.product.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.PaginationDto
import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.product.data.models.response.ProductResponseDto
import com.luckyfrog.quickmart.features.product.domain.entities.ProductFormParamsEntity

class ProductRemoteDataSourceImpl (
    private val api: ProductApi
) : ProductRemoteDataSource {

    override suspend fun getProducts(
        params: ProductFormParamsEntity
    ): ResponseDto<PaginationDto<ProductResponseDto>> {
        return api.getProducts(
            categoryId = params.categoryId,
            query = params.query,
            queryBy = params.queryBy,
            sortBy = params.sortBy,
            order = params.sortOrder,
            page = params.page,
            limit = params.limit,
        )
    }

    override suspend fun getProductDetail(id: String): ResponseDto<ProductResponseDto> {
        return api.getProductDetail(id = id)
    }
}