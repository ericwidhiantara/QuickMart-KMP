package com.luckyfrog.quickmart.features.review.domain.repositories

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.review.domain.entities.CreateReviewParamsEntity
import com.luckyfrog.quickmart.features.review.domain.entities.GetProductReviewsParamsEntity
import com.luckyfrog.quickmart.features.review.domain.entities.ReviewEntity
import com.luckyfrog.quickmart.features.review.domain.entities.ReviewSummaryEntity
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    suspend fun createReview(params: CreateReviewParamsEntity): Flow<ApiResponse<ResponseDto<ReviewEntity>>>
    suspend fun getProductReviews(params: GetProductReviewsParamsEntity): Flow<ApiResponse<ResponseDto<ReviewSummaryEntity>>>
    suspend fun deleteReview(reviewId: String): Flow<ApiResponse<ResponseDto<Unit>>>
}
