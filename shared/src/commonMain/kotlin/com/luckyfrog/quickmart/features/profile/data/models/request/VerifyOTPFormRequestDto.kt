package com.luckyfrog.quickmart.features.profile.data.models.request

data class VerifyOTPFormRequestDto(
    val otpCode: String,
)

data class SendOTPFormRequestDto(
    val token: String,
)