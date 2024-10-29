package com.luckyfrog.quickmart.features.auth.data.models.mapper

import com.luckyfrog.quickmart.features.auth.data.models.response.LoginResponseDto
import com.luckyfrog.quickmart.features.auth.domain.entities.LoginEntity


// Mapping from DTO to Entity
fun LoginResponseDto.toEntity() = LoginEntity(
    id = this.id,
    username = this.username,
    email = this.email,
    firstName = this.firstName,
    lastName = this.lastName,
    gender = this.gender,
    image = this.image,
    accessToken = this.accessToken,
    refreshToken = this.refreshToken,
    message = this.message
)

// Mapping from Entity to DTO
fun LoginEntity.toDto() = LoginResponseDto(
    id = this.id ?: 0,
    username = this.username ?: "",
    email = this.email ?: "",
    firstName = this.firstName ?: "",
    lastName = this.lastName ?: "",
    gender = this.gender ?: "",
    image = this.image ?: "",
    accessToken = this.accessToken ?: "",
    refreshToken = this.refreshToken ?: "",
    message = this.message ?: ""
)

// Mapping lists
fun List<LoginResponseDto>.toEntityList(): List<LoginEntity> {
    return this.map { it.toEntity() }
}

fun List<LoginEntity>.toDtoList(): List<LoginResponseDto> {
    return this.map { it.toDto() }
}
