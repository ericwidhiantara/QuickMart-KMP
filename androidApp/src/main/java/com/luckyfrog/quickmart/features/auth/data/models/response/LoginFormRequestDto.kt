package com.luckyfrog.quickmart.features.auth.data.models.response

import com.google.gson.annotations.SerializedName

data class LoginFormRequestDto(
    @SerializedName("email_or_username")
    val emailOrUsername: String,
    val password: String,
)