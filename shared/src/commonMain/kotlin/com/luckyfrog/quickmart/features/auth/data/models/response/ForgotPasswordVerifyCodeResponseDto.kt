package com.luckyfrog.quickmart.features.auth.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordVerifyCodeResponseDto(
    @SerialName("otp_id")
    val otpId: String?
)