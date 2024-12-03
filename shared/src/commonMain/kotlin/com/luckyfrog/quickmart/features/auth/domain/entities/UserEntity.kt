package com.luckyfrog.quickmart.features.auth.domain.entities

data class UserEntity(
    val id: String, // Use a String for the UUID
    val role: String?,
    val fullname: String?, // Renamed from first and last name
    val username: String?,
    val email: String?,
    val phoneNumber: String?, // Renamed from phone
    val gender: String?,
     val birthDate: String?,
    val image: String? // Image remains the same
)
