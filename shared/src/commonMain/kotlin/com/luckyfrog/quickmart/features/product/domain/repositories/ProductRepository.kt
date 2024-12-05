package com.luckyfrog.quickmart.features.product.domain.repositories

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.entities.PaginationEntity
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import com.luckyfrog.quickmart.features.product.domain.entities.ProductFormParamsEntity
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProducts(params: ProductFormParamsEntity): Flow<ApiResponse<ResponseDto<PaginationEntity<ProductEntity>>>>

    suspend fun getProductDetail(id: String): Flow<ApiResponse<ResponseDto<ProductEntity>>>
}