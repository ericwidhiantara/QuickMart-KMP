package com.luckyfrog.quickmart.features.category.data.datasources.remote

import com.luckyfrog.quickmart.features.category.data.models.response.CategoryResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryApi {

    @GET("categories")
    suspend fun getCategories(
        @Query("query") query: String?,
        @Query("query_by") queryBy: String?,
        @Query("sort_by") sortBy: String = "created_at",
        @Query("sort_order") order: String = "desc",
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
    ): CategoryResponseDto

}