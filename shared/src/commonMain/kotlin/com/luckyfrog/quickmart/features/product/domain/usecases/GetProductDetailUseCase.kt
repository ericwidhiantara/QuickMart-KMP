package com.luckyfrog.quickmart.features.product.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import com.luckyfrog.quickmart.features.product.domain.repositories.ProductRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class GetProductDetailUseCase(
    private val repository: ProductRepository
) : UseCase<String, Flow<ApiResponse<ResponseDto<ProductEntity>>>> {
    override suspend fun execute(input: String): Flow<ApiResponse<ResponseDto<ProductEntity>>> {
        return repository.getProductDetail(input)
    }
}