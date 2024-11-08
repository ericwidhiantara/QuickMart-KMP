package com.luckyfrog.quickmart.features.auth.data.models.response

data class ForgotPasswordVerifyOTPFormRequestDto(
    val email: String,
    val otpCode: String
)