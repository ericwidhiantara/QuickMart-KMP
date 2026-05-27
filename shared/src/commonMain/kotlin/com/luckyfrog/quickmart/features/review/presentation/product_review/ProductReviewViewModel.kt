package com.luckyfrog.quickmart.features.review.presentation.product_review

import com.luckyfrog.quickmart.features.review.domain.entities.CreateReviewParamsEntity
import com.luckyfrog.quickmart.features.review.domain.entities.GetProductReviewsParamsEntity
import com.luckyfrog.quickmart.features.review.domain.entities.ReviewEntity
import com.luckyfrog.quickmart.features.review.domain.entities.ReviewSummaryEntity
import com.luckyfrog.quickmart.features.review.domain.usecases.CreateReviewUseCase
import com.luckyfrog.quickmart.features.review.domain.usecases.DeleteReviewUseCase
import com.luckyfrog.quickmart.features.review.domain.usecases.GetProductReviewsUseCase
import com.luckyfrog.quickmart.utils.ApiResponse
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ReviewListState {
    data object Idle : ReviewListState()
    data object Loading : ReviewListState()
    data class Success(val summary: ReviewSummaryEntity) : ReviewListState()
    data class Error(val message: String) : ReviewListState()
}

sealed class CreateReviewState {
    data object Idle : CreateReviewState()
    data object Loading : CreateReviewState()
    data class Success(val review: ReviewEntity) : CreateReviewState()
    data class Error(val message: String) : CreateReviewState()
}

sealed class DeleteReviewState {
    data object Idle : DeleteReviewState()
    data object Loading : DeleteReviewState()
    data object Success : DeleteReviewState()
    data class Error(val message: String) : DeleteReviewState()
}

class ProductReviewViewModel(
    private val getProductReviewsUseCase: GetProductReviewsUseCase,
    private val createReviewUseCase: CreateReviewUseCase,
    private val deleteReviewUseCase: DeleteReviewUseCase
) : ViewModel() {

    private val _reviewListState = MutableStateFlow<ReviewListState>(ReviewListState.Idle)
    val reviewListState = _reviewListState.asStateFlow().cStateFlow()

    private val _createReviewState = MutableStateFlow<CreateReviewState>(CreateReviewState.Idle)
    val createReviewState = _createReviewState.asStateFlow().cStateFlow()

    private val _deleteReviewState = MutableStateFlow<DeleteReviewState>(DeleteReviewState.Idle)
    val deleteReviewState = _deleteReviewState.asStateFlow().cStateFlow()

    fun fetchReviews(
        productId: String,
        rating: Int? = null,
        sortBy: String = "created_at",
        sortOrder: String = "desc",
        page: Int = 1,
        limit: Int = 10
    ) {
        _reviewListState.value = ReviewListState.Loading
        viewModelScope.launch {
            val params = GetProductReviewsParamsEntity(
                productId = productId,
                rating = rating,
                sortBy = sortBy,
                sortOrder = sortOrder,
                page = page,
                limit = limit
            )
            getProductReviewsUseCase.execute(params).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        val summary = response.data.data
                        if (summary != null) {
                            _reviewListState.value = ReviewListState.Success(summary = summary)
                        } else {
                            _reviewListState.value = ReviewListState.Error("No data")
                        }
                    }
                    is ApiResponse.Failure -> {
                        _reviewListState.value = ReviewListState.Error(response.errorMessage)
                    }
                    else -> {}
                }
            }
        }
    }

    fun createReview(productId: String, rating: Int, comment: String? = null) {
        _createReviewState.value = CreateReviewState.Loading
        viewModelScope.launch {
            val params = CreateReviewParamsEntity(
                productId = productId,
                rating = rating,
                comment = comment
            )
            createReviewUseCase.execute(params).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        val review = response.data.data
                        if (review != null) {
                            _createReviewState.value = CreateReviewState.Success(review = review)
                        } else {
                            _createReviewState.value = CreateReviewState.Error("Failed to submit review")
                        }
                    }
                    is ApiResponse.Failure -> {
                        _createReviewState.value = CreateReviewState.Error(response.errorMessage)
                    }
                    else -> {}
                }
            }
        }
    }

    fun deleteReview(reviewId: String) {
        _deleteReviewState.value = DeleteReviewState.Loading
        viewModelScope.launch {
            deleteReviewUseCase.execute(reviewId).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        _deleteReviewState.value = DeleteReviewState.Success
                    }
                    is ApiResponse.Failure -> {
                        _deleteReviewState.value = DeleteReviewState.Error(response.errorMessage)
                    }
                    else -> {}
                }
            }
        }
    }

    fun resetCreateState() { _createReviewState.value = CreateReviewState.Idle }
    fun resetDeleteState() { _deleteReviewState.value = DeleteReviewState.Idle }
}
