package com.luckyfrog.quickmart.features.profile.data.models.request

data class ChangePasswordFormRequestDto(
    val newPassword: String,
    val confirmPassword: String
)
