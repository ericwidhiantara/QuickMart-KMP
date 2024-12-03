// AuthResponseDto.kt
package com.luckyfrog.quickmart.features.auth.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDto(

    @SerialName("access_token")
    val accessToken: String?,

    @SerialName("refresh_token")
    val refreshToken: String?,
)
