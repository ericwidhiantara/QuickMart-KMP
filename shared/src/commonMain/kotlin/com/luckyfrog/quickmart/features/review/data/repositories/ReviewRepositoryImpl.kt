package com.luckyfrog.quickmart.features.review.data.repositories

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.network.processResponse
import com.luckyfrog.quickmart.features.review.data.datasources.remote.ReviewRemoteDataSource
import com.luckyfrog.quickmart.features.review.data.models.mapper.toEntity
import com.luckyfrog.quickmart.features.review.domain.entities.CreateReviewParamsEntity
import com.luckyfrog.quickmart.features.review.domain.entities.GetProductReviewsParamsEntity
import com.luckyfrog.quickmart.features.review.domain.entities.ReviewEntity
import com.luckyfrog.quickmart.features.review.domain.entities.ReviewSummaryEntity
import com.luckyfrog.quickmart.features.review.domain.repositories.ReviewRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReviewRepositoryImpl(
    private val remoteDataSource: ReviewRemoteDataSource
) : ReviewRepository {

    override suspend fun createReview(params: CreateReviewParamsEntity): Flow<ApiResponse<ResponseDto<ReviewEntity>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.createReview(params = params)
            emit(processResponse(response) { it.data?.toEntity() })
        }

    override suspend fun getProductReviews(params: GetProductReviewsParamsEntity): Flow<ApiResponse<ResponseDto<ReviewSummaryEntity>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.getProductReviews(params = params)
            emit(processResponse(response) { it.data?.toEntity() })
        }

    override suspend fun deleteReview(reviewId: String): Flow<ApiResponse<ResponseDto<Unit>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.deleteReview(reviewId = reviewId)
            emit(processResponse(response) { Unit })
        }
}
