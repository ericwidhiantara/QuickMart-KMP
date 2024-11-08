package com.luckyfrog.quickmart.features.auth.data.models.response

import com.google.gson.annotations.SerializedName

data class ForgotPasswordVerifyCodeResponseDto(
    @SerializedName("otp_id")
    val otpId: String?
)