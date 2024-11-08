package com.luckyfrog.quickmart.features.auth.data.models.request

data class ForgotPasswordChangePasswordFormRequestDto(
    val otpId: String,
    val newPassword: String,
    val confirmPassword: String
)
