package com.luckyfrog.quickmart.features.category.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.entities.PaginationEntity
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryEntity
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryFormParamsEntity
import com.luckyfrog.quickmart.features.category.domain.repositories.CategoryRepository
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) : UseCase<CategoryFormParamsEntity, Flow<ApiResponse<ResponseDto<PaginationEntity<CategoryEntity>>>>> {
    override suspend fun execute(input: CategoryFormParamsEntity): Flow<ApiResponse<ResponseDto<PaginationEntity<CategoryEntity>>>> {
        return repository.getCategories(input)
    }
}