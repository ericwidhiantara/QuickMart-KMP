package com.luckyfrog.quickmart.features.review.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.review.domain.repositories.ReviewRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class DeleteReviewUseCase(
    private val repository: ReviewRepository
) : UseCase<String, Flow<ApiResponse<ResponseDto<Unit>>>> {
    override suspend fun execute(input: String): Flow<ApiResponse<ResponseDto<Unit>>> {
        return repository.deleteReview(input)
    }
}
