package com.luckyfrog.quickmart.features.auth.data.models.response

data class RefreshTokenFormRequestDto(
    val refreshToken: String,
    val expiresInMins: Int
)