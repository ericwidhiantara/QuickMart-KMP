package com.luckyfrog.quickmart.features.auth.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDto(
    @SerialName("id") val id: String,
    @SerialName("role") val role: String?,
    @SerialName("fullname") val fullname: String?,
    @SerialName("username") val username: String?,
    @SerialName("email") val email: String?,
    @SerialName("phone_number") val phoneNumber: String?,
    @SerialName("gender") val gender: String?,
    @SerialName("birth_date") val birthDate: String?,
    @SerialName("profile_picture") val profilePicture: String?
)
