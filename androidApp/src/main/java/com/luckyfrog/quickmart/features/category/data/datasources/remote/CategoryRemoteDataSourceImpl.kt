package com.luckyfrog.quickmart.features.product.data.datasources.remote

import com.luckyfrog.quickmart.features.category.data.datasources.remote.CategoryApi
import com.luckyfrog.quickmart.features.category.data.datasources.remote.CategoryRemoteDataSource
import com.luckyfrog.quickmart.features.category.data.models.response.CategoryResponseDto
import javax.inject.Inject

class CategoryRemoteDataSourceImpl @Inject constructor(
    private val api: CategoryApi
) : CategoryRemoteDataSource {

    override suspend fun getCategories(
    ): List<CategoryResponseDto> {
        return api.getCategories()
    }


}