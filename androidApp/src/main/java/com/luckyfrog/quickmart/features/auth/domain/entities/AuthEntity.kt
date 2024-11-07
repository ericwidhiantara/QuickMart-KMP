package com.luckyfrog.quickmart.features.auth.domain.entities

data class AuthEntity(
    val accessToken: String?,
    val refreshToken: String?,
)
