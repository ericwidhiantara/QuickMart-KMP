package com.luckyfrog.quickmart.features.auth.domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenFormParamsEntity(
    @SerialName("refresh_token")
    val refreshToken: String,
)
@Serializable
data class CheckTokenFormParamsEntity(
    @SerialName("access_token")
    val accessToken: String
)