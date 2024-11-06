package com.luckyfrog.quickmart.features.auth.domain.entities

data class LoginEntity(
    val accessToken: String?,
    val refreshToken: String?,
)
