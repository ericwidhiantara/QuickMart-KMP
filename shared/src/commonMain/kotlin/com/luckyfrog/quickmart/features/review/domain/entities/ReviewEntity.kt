package com.luckyfrog.quickmart.features.review.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class ReviewEntity(
    val id: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val userId: String?,
    val productId: String?,
    val rating: Int?,
    val comment: String?,
    val reviewerName: String?
)

@Serializable
data class ReviewSummaryEntity(
    val averageRating: Double,
    val totalReviews: Int,
    val reviews: List<ReviewEntity>
)

@Serializable
data class CreateReviewParamsEntity(
    val productId: String,
    val rating: Int,
    val comment: String? = null
)

@Serializable
data class GetProductReviewsParamsEntity(
    val productId: String,
    val rating: Int? = null,
    val sortBy: String = "created_at",
    val sortOrder: String = "desc",
    val page: Int = 1,
    val limit: Int = 10
)
