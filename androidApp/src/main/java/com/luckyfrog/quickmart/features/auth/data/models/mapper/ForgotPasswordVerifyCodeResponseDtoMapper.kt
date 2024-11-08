package com.luckyfrog.quickmart.features.auth.data.models.mapper

import com.luckyfrog.quickmart.features.auth.data.models.response.ForgotPasswordVerifyCodeResponseDto
import com.luckyfrog.quickmart.features.auth.domain.entities.ForgotPasswordVerifyCodeResponseEntity

// Mapping from DTO to Entity
fun ForgotPasswordVerifyCodeResponseDto.toEntity() = ForgotPasswordVerifyCodeResponseEntity(
    otpId = this.otpId,
)

// Mapping from Entity to DTO
fun ForgotPasswordVerifyCodeResponseEntity.toDto() = ForgotPasswordVerifyCodeResponseDto(
    otpId = otpId
)

// Mapping lists
fun List<ForgotPasswordVerifyCodeResponseDto>.toEntityList(): List<ForgotPasswordVerifyCodeResponseEntity> {
    return this.map { it.toEntity() }
}

fun List<ForgotPasswordVerifyCodeResponseEntity>.toDtoList(): List<ForgotPasswordVerifyCodeResponseDto> {
    return this.map { it.toDto() }
}
