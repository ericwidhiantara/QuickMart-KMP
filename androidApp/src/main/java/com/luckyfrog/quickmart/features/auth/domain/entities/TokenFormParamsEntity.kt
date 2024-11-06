package com.luckyfrog.quickmart.features.auth.domain.entities

import com.google.gson.annotations.SerializedName

data class RefreshTokenFormParamsEntity(
    @SerializedName("refresh_token")
    val refreshToken: String,
)

data class CheckTokenFormParamsEntity(
    @SerializedName("access_token")
    val accessToken: String
)