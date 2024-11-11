package com.luckyfrog.quickmart.features.category.domain.usecases

import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryEntity
import com.luckyfrog.quickmart.features.category.domain.repositories.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) : UseCase<Unit, CategoryEntity> {
    override suspend fun execute(input: Unit): CategoryEntity {
        return repository.getCategories()
    }
}