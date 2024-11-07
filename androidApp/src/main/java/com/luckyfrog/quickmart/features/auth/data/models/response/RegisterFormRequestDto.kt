package com.luckyfrog.quickmart.features.auth.data.models.response

data class RegisterFormRequestDto(
    val fullname: String,
    val username: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
)