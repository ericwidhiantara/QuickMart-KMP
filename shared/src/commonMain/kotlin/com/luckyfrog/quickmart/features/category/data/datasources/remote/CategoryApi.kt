package com.luckyfrog.quickmart.features.category.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.PaginationDto
import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.category.data.models.response.CategoryResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

interface CategoryApi {

    suspend fun getCategories(
        query: String?,
        queryBy: String?,
        sortBy: String = "created_at",
        order: String = "desc",
        page: Int?,
        limit: Int?,
    ): ResponseDto<PaginationDto<CategoryResponseDto>>

}

class CategoryApiImpl(private val client: HttpClient) : CategoryApi {
    override suspend fun getCategories(
        query: String?,
        queryBy: String?,
        sortBy: String,
        order: String,
        page: Int?,
        limit: Int?
    ): ResponseDto<PaginationDto<CategoryResponseDto>> {
        val response = client.get("categories") {
            if (query != null) {
                parameter("query", query)
                parameter("query_by", queryBy)
            }
            parameter("sort_by", sortBy)
            parameter("sort_order", order)
            parameter("page", page)
            parameter("limit", limit)
        }
        return response.body()
    }
}