package com.luckyfrog.quickmart.features.review.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.review.data.models.response.CreateReviewRequestDto
import com.luckyfrog.quickmart.features.review.data.models.response.ReviewResponseDto
import com.luckyfrog.quickmart.features.review.data.models.response.ReviewSummaryResponseDto
import com.luckyfrog.quickmart.features.review.domain.entities.CreateReviewParamsEntity
import com.luckyfrog.quickmart.features.review.domain.entities.GetProductReviewsParamsEntity

interface ReviewRemoteDataSource {
    suspend fun createReview(params: CreateReviewParamsEntity): ResponseDto<ReviewResponseDto>
    suspend fun getProductReviews(params: GetProductReviewsParamsEntity): ResponseDto<ReviewSummaryResponseDto>
    suspend fun deleteReview(reviewId: String): ResponseDto<Unit>
}

class ReviewRemoteDataSourceImpl(
    private val api: ReviewApi
) : ReviewRemoteDataSource {

    override suspend fun createReview(params: CreateReviewParamsEntity): ResponseDto<ReviewResponseDto> {
        return api.createReview(
            productId = params.productId,
            body = CreateReviewRequestDto(
                productId = params.productId,
                rating = params.rating,
                comment = params.comment
            )
        )
    }

    override suspend fun getProductReviews(params: GetProductReviewsParamsEntity): ResponseDto<ReviewSummaryResponseDto> {
        return api.getProductReviews(
            productId = params.productId,
            rating = params.rating,
            sortBy = params.sortBy,
            sortOrder = params.sortOrder,
            page = params.page,
            limit = params.limit
        )
    }

    override suspend fun deleteReview(reviewId: String): ResponseDto<Unit> {
        return api.deleteReview(reviewId = reviewId)
    }
}
