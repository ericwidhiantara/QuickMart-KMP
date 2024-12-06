package com.luckyfrog.quickmart.features.auth.domain.entities

data class UserEntity(
    val id: String,
    val role: String?,
    val fullname: String?,
    val username: String?,
    val email: String?,
    val phoneNumber: String?,
    val gender: String?,
    val birthDate: String?,
    val profilePicture: String?
)
