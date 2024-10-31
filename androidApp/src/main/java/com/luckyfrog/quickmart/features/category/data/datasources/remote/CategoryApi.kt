package com.luckyfrog.quickmart.features.category.data.datasources.remote

import com.luckyfrog.quickmart.features.category.data.models.response.CategoryResponseDto
import retrofit2.http.GET

interface CategoryApi {

    @GET("products/categories")
    suspend fun getCategories(
    ): List<CategoryResponseDto>

}