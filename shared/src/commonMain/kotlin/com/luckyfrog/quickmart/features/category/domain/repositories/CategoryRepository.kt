package com.luckyfrog.quickmart.features.category.domain.repositories

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.entities.PaginationEntity
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryEntity
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryFormParamsEntity
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategories(params: CategoryFormParamsEntity): Flow<ApiResponse<ResponseDto<PaginationEntity<CategoryEntity>>>>


}