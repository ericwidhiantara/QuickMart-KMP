package com.luckyfrog.quickmart.features.auth.data.models.mapper

import com.luckyfrog.quickmart.features.auth.data.models.response.UserResponseDto
import com.luckyfrog.quickmart.features.auth.domain.entities.UserEntity

/**
 * Mapping from UserResponseDto to com.luckyfrog.quickmart.features.auth.domain.entities.UserEntity
 */
fun UserResponseDto.toEntity() = UserEntity(
    id = this.id,
    fullname = this.fullname ?: "",
    username = this.username ?: "",
    email = this.email ?: "",
    phoneNumber = this.phoneNumber ?: "",
    gender = this.gender ?: "",
    birthDate = this.birthDate ?: "",
    image = this.image ?: "",
    role = this.role ?: ""
)

/**
 * Mapping from com.luckyfrog.quickmart.features.auth.domain.entities.UserEntity to UserResponseDto
 */
fun UserEntity.toDto() = UserResponseDto(
    id = this.id,
    fullname = this.fullname ?: "",
    username = this.username ?: "",
    email = this.email ?: "",
    phoneNumber = this.phoneNumber ?: "",
    gender = this.gender ?: "",
    birthDate = this.birthDate ?: "",
    image = this.image ?: "",
    role = this.role ?: ""
)

// Mapping lists
fun List<UserResponseDto>.toEntityList(): List<UserEntity> {
    return this.map { it.toEntity() }
}

fun List<UserEntity>.toDtoList(): List<UserResponseDto> {
    return this.map { it.toDto() }
}
