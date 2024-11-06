package com.luckyfrog.quickmart.features.auth.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String, // Use a String for the UUID
    @ColumnInfo(name = "role") val role: String?,
    @ColumnInfo(name = "fullname") val fullname: String?, // Renamed from first and last name
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "phone_number") val phoneNumber: String?, // Renamed from phone
    @ColumnInfo(name = "gender") val gender: String?,
    @ColumnInfo(name = "birth_date") val birthDate: String?,
    @ColumnInfo(name = "image") val image: String? // Image remains the same
)
