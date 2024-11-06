package com.luckyfrog.quickmart.features.auth.data.models.mapper

import com.luckyfrog.quickmart.features.auth.data.models.response.LoginResponseDto
import com.luckyfrog.quickmart.features.auth.domain.entities.LoginEntity

// Mapping from DTO to Entity
fun LoginResponseDto.toEntity() = LoginEntity(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)

// Mapping from Entity to DTO
fun LoginEntity.toDto() = LoginResponseDto(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)

// Mapping lists
fun List<LoginResponseDto>.toEntityList(): List<LoginEntity> {
    return this.map { it.toEntity() }
}

fun List<LoginEntity>.toDtoList(): List<LoginResponseDto> {
    return this.map { it.toDto() }
}
