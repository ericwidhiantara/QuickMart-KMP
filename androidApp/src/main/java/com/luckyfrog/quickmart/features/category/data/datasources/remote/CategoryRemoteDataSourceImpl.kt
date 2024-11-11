package com.luckyfrog.quickmart.features.product.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.PaginationDto
import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.category.data.datasources.remote.CategoryApi
import com.luckyfrog.quickmart.features.category.data.datasources.remote.CategoryRemoteDataSource
import com.luckyfrog.quickmart.features.category.data.models.response.CategoryResponseDto
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryFormParamsEntity
import com.luckyfrog.quickmart.utils.helper.Constants
import retrofit2.Response
import javax.inject.Inject

class CategoryRemoteDataSourceImpl @Inject constructor(
    private val api: CategoryApi
) : CategoryRemoteDataSource {

    override suspend fun getCategories(
    ): Response<ResponseDto<PaginationDto<CategoryResponseDto>>> {
        val params = CategoryFormParamsEntity(
            query = null,
            queryBy = "name",
            sortBy = "created_at",
            sortOrder = "asc",
            limit = Constants.MAX_PAGE_SIZE,
            page = 1
        )
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