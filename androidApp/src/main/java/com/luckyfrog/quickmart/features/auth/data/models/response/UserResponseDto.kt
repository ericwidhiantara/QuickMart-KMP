package com.luckyfrog.quickmart.features.auth.data.models.response

import kotlinx.serialization.SerialName

data class UserResponseDto(
    @SerialName("id") val id: String, // Changed to String for UUID
    @SerialName("role") val role: String?,
    @SerialName("fullname") val fullname: String?, // Combined firstName and lastName
    @SerialName("username") val username: String?,
    @SerialName("email") val email: String?,
    @SerialName("phone_number") val phoneNumber: String?, // Renamed from phone
    @SerialName("gender") val gender: String?,
    @SerialName("birth_date") val birthDate: String?,
    @SerialName("image") val image: String? // Image remains the same
)
