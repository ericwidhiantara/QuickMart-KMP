package com.luckyfrog.quickmart.features.auth.data.models.mapper

import com.luckyfrog.quickmart.features.auth.data.models.response.AuthResponseDto
import com.luckyfrog.quickmart.features.auth.domain.entities.AuthEntity

// Mapping from DTO to Entity
fun AuthResponseDto.toEntity() = AuthEntity(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)

// Mapping from Entity to DTO
fun AuthEntity.toDto() = AuthResponseDto(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)

// Mapping lists
fun List<AuthResponseDto>.toEntityList(): List<AuthEntity> {
    return this.map { it.toEntity() }
}

fun List<AuthEntity>.toDtoList(): List<AuthResponseDto> {
    return this.map { it.toDto() }
}
