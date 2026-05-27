package com.luckyfrog.quickmart.features.review.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.review.domain.entities.CreateReviewParamsEntity
import com.luckyfrog.quickmart.features.review.domain.entities.ReviewEntity
import com.luckyfrog.quickmart.features.review.domain.repositories.ReviewRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class CreateReviewUseCase(
    private val repository: ReviewRepository
) : UseCase<CreateReviewParamsEntity, Flow<ApiResponse<ResponseDto<ReviewEntity>>>> {
    override suspend fun execute(input: CreateReviewParamsEntity): Flow<ApiResponse<ResponseDto<ReviewEntity>>> {
        return repository.createReview(input)
    }
}
