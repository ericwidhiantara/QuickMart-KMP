package com.luckyfrog.quickmart.features.auth.data.models.request

data class LoginFormRequestDto(
    val username: String,
    val password: String,
)