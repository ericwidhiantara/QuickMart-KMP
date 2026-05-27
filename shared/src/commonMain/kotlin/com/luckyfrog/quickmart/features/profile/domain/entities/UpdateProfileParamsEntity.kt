package com.luckyfrog.quickmart.features.profile.domain.entities

data class UpdateProfileParamsEntity(
    val fullname: String? = null,
    val username: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val gender: String? = null,
    val birthDate: String? = null,
    val language: String? = null,
    val currency: String? = null
)
