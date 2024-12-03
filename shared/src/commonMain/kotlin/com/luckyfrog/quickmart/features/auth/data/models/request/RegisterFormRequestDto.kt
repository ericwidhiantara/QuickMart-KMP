package com.luckyfrog.quickmart.features.auth.data.models.request

data class RegisterFormRequestDto(
    val fullname: String,
    val username: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
)