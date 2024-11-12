package com.luckyfrog.quickmart.features.category.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.PaginationDto
import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.category.data.models.response.CategoryResponseDto
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryFormParamsEntity
import retrofit2.Response

interface CategoryRemoteDataSource {

    suspend fun getCategories(
        params: CategoryFormParamsEntity
    ): Response<ResponseDto<PaginationDto<CategoryResponseDto>>>

}
