package com.luckyfrog.quickmart.features.category.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.PaginationDto
import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.category.data.models.response.CategoryResponseDto
import retrofit2.Response

interface CategoryRemoteDataSource {

    suspend fun getCategories(
    ): Response<ResponseDto<PaginationDto<CategoryResponseDto>>>

}
