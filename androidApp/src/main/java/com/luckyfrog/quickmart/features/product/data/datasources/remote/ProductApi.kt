package com.luckyfrog.quickmart.features.product.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.product.data.models.response.ProductResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
        @Query("order") order: String = "asc",
        @Query("sortBy") sortBy: String = "createdAt",
    ): ResponseDto<List<ProductResponseDto>>

    @GET("products/category/{category_name}")
    suspend fun getProductsByCategory(
        @Path("category_name") category: String,
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
        @Query("order") order: String = "asc",
        @Query("sortBy") sortBy: String = "createdAt",
    ): ResponseDto<List<ProductResponseDto>>

    @GET("products/search")
    suspend fun searchProduct(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
        @Query("order") order: String = "asc",
        @Query("sortBy") sortBy: String = "createdAt",
        @Query("q") q: String,
    ): ResponseDto<List<ProductResponseDto>>

    @GET("products/{id}")
    suspend fun getProductDetail(
        @Path("id") id: Int
    ): ProductResponseDto
}