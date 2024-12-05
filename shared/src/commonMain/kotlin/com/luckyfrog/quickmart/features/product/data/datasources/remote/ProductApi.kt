package com.luckyfrog.quickmart.features.product.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.PaginationDto
import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.product.data.models.response.ProductResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

interface ProductApi {
    suspend fun getProducts(
        categoryId: String?,
        query: String?,
        queryBy: String?,
        sortBy: String = "created_at",
        order: String = "desc",
        page: Int?,
        limit: Int?,
    ): ResponseDto<PaginationDto<ProductResponseDto>>


    suspend fun getProductDetail(
        id: String
    ): ResponseDto<ProductResponseDto>

}

class ProductApiImpl(private val client: HttpClient) : ProductApi {

    override suspend fun getProducts(
        categoryId: String?,
        query: String?,
        queryBy: String?,
        sortBy: String,
        order: String,
        page: Int?,
        limit: Int?,
    ): ResponseDto<PaginationDto<ProductResponseDto>> {
        val response =  client.get("product") {
            if (categoryId != null) {
                parameter("category_id", categoryId)
            }
            if (query != null) {
                parameter("query", query)
            }
            if (queryBy != null) {
                parameter("query_by", queryBy)
            }
            parameter("sort_by", sortBy)
            parameter("order", order)
            if (page != null) {
                parameter("page", page)
            }
            if (limit != null) {
                parameter("limit", limit)
            }
        }
        return response.body()
    }

    override suspend fun getProductDetail(id: String): ResponseDto<ProductResponseDto> {
        val response = client.get("product/$id")
        return response.body()
    }
}