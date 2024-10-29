package com.luckyfrog.quickmart.features.auth.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class LoginEntity(
    val id: Int?,
    val username: String?,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val gender: String?,
    val image: String?,
    val accessToken: String?,
    val refreshToken: String?,
    val message: String?,
)
