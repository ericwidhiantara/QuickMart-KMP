package com.luckyfrog.quickmart.features.product.domain.usecases

import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import com.luckyfrog.quickmart.features.product.domain.repositories.ProductRepository
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val repository: ProductRepository
) : UseCase<Int, ProductEntity> {
    override suspend fun execute(input: Int): ProductEntity {
        return repository.getProductDetail(input)
    }
}