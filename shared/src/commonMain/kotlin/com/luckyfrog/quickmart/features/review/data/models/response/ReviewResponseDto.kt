package com.luckyfrog.quickmart.features.review.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewResponseDto(
    val id: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    @SerialName("user_id")
    val userId: String? = null,
    @SerialName("product_id")
    val productId: String? = null,
    val rating: Int? = null,
    val comment: String? = null,
    @SerialName("reviewer_name")
    val reviewerName: String? = null
)

@Serializable
data class ReviewSummaryResponseDto(
    @SerialName("average_rating")
    val averageRating: Double = 0.0,
    @SerialName("total_reviews")
    val totalReviews: Int = 0,
    val reviews: List<ReviewResponseDto> = emptyList()
)

@Serializable
data class CreateReviewRequestDto(
    @SerialName("product_id")
    val productId: String,
    val rating: Int,
    val comment: String? = null
)
