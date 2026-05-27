package com.luckyfrog.quickmart.features.review.data.models.mapper

import com.luckyfrog.quickmart.features.review.data.models.response.ReviewResponseDto
import com.luckyfrog.quickmart.features.review.data.models.response.ReviewSummaryResponseDto
import com.luckyfrog.quickmart.features.review.domain.entities.ReviewEntity
import com.luckyfrog.quickmart.features.review.domain.entities.ReviewSummaryEntity

fun ReviewResponseDto.toEntity() = ReviewEntity(
    id = id,
    createdAt = createdAt,
    updatedAt = updatedAt,
    userId = userId,
    productId = productId,
    rating = rating,
    comment = comment,
    reviewerName = reviewerName
)

fun ReviewSummaryResponseDto.toEntity() = ReviewSummaryEntity(
    averageRating = averageRating,
    totalReviews = totalReviews,
    reviews = reviews.map { it.toEntity() }
)

fun List<ReviewResponseDto>.toEntityList(): List<ReviewEntity> = map { it.toEntity() }
