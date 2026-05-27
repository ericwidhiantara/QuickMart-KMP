package com.luckyfrog.quickmart.features.review.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.review.domain.entities.GetProductReviewsParamsEntity
import com.luckyfrog.quickmart.features.review.domain.entities.ReviewSummaryEntity
import com.luckyfrog.quickmart.features.review.domain.repositories.ReviewRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class GetProductReviewsUseCase(
    private val repository: ReviewRepository
) : UseCase<GetProductReviewsParamsEntity, Flow<ApiResponse<ResponseDto<ReviewSummaryEntity>>>> {
    override suspend fun execute(input: GetProductReviewsParamsEntity): Flow<ApiResponse<ResponseDto<ReviewSummaryEntity>>> {
        return repository.getProductReviews(input)
    }
}
