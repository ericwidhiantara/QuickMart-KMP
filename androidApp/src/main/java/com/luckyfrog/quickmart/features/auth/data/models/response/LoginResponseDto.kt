// LoginResponseDto.kt
package com.luckyfrog.quickmart.features.auth.data.models.response

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(

    @SerializedName("access_token")
    val accessToken: String?,

    @SerializedName("refresh_token")
    val refreshToken: String?,
)
