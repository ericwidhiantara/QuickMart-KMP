package com.luckyfrog.quickmart.features.product.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.entities.PaginationEntity
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import com.luckyfrog.quickmart.features.product.domain.entities.ProductFormParamsEntity
import com.luckyfrog.quickmart.features.product.domain.repositories.ProductRepository
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) : UseCase<ProductFormParamsEntity, Flow<ApiResponse<ResponseDto<PaginationEntity<ProductEntity>>>>> {
    override suspend fun execute(input: ProductFormParamsEntity): Flow<ApiResponse<ResponseDto<PaginationEntity<ProductEntity>>>> {
        return repository.getProducts(input)
    }
}