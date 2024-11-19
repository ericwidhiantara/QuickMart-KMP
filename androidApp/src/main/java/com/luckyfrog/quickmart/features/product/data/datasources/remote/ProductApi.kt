package com.luckyfrog.quickmart.features.product.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.PaginationDto
import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.product.data.models.response.ProductResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun getProducts(
        @Query("category_id") categoryId: String?,
        @Query("query") query: String?,
        @Query("query_by") queryBy: String?,
        @Query("sort_by") sortBy: String = "created_at",
        @Query("sort_order") order: String = "desc",
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
    ): Response<ResponseDto<PaginationDto<ProductResponseDto>>>

    @GET("products/{id}")
    suspend fun getProductDetail(
        @Path("id") id: String
    ): Response<ResponseDto<ProductResponseDto>>
}