package com.luckyfrog.quickmart.features.review.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.review.data.models.response.CreateReviewRequestDto
import com.luckyfrog.quickmart.features.review.data.models.response.ReviewResponseDto
import com.luckyfrog.quickmart.features.review.data.models.response.ReviewSummaryResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface ReviewApi {
    suspend fun createReview(
        productId: String,
        body: CreateReviewRequestDto
    ): ResponseDto<ReviewResponseDto>

    suspend fun getProductReviews(
        productId: String,
        rating: Int?,
        sortBy: String,
        sortOrder: String,
        page: Int,
        limit: Int
    ): ResponseDto<ReviewSummaryResponseDto>

    suspend fun deleteReview(reviewId: String): ResponseDto<Unit>
}

class ReviewApiImpl(private val client: HttpClient) : ReviewApi {

    override suspend fun createReview(
        productId: String,
        body: CreateReviewRequestDto
    ): ResponseDto<ReviewResponseDto> {
        val response = client.post("products/$productId/reviews") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        return response.body()
    }

    override suspend fun getProductReviews(
        productId: String,
        rating: Int?,
        sortBy: String,
        sortOrder: String,
        page: Int,
        limit: Int
    ): ResponseDto<ReviewSummaryResponseDto> {
        val response = client.get("products/$productId/reviews") {
            if (rating != null) parameter("rating", rating)
            parameter("sort_by", sortBy)
            parameter("sort_order", sortOrder)
            parameter("page", page)
            parameter("limit", limit)
        }
        return response.body()
    }

    override suspend fun deleteReview(reviewId: String): ResponseDto<Unit> {
        val response = client.delete("reviews/$reviewId")
        return response.body()
    }
}
