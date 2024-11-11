package com.luckyfrog.quickmart.features.category.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.PaginationDto
import com.luckyfrog.quickmart.features.category.data.models.response.CategoryResponseDto

interface CategoryRemoteDataSource {

    suspend fun getCategories(
    ): PaginationDto<CategoryResponseDto>

}
