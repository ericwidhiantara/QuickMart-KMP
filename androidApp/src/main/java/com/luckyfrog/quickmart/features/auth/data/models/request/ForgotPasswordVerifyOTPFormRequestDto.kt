package com.luckyfrog.quickmart.features.auth.data.models.request

data class ForgotPasswordVerifyOTPFormRequestDto(
    val email: String,
    val otpCode: String
)