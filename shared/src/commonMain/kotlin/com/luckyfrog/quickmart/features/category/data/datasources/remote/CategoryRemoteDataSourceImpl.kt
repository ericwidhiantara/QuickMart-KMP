package com.luckyfrog.quickmart.features.category.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.PaginationDto
import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.category.data.models.response.CategoryResponseDto
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryFormParamsEntity

class CategoryRemoteDataSourceImpl(
    private val api: CategoryApi
) : CategoryRemoteDataSource {

    override suspend fun getCategories(
        params: CategoryFormParamsEntity

    ): ResponseDto<PaginationDto<CategoryResponseDto>> {
        return api.getCategories(
            query = params.query,
            queryBy = params.queryBy,
            sortBy = params.sortBy,
            order = params.sortOrder,
            page = params.page,
            limit = params.limit
        )
    }


}