package com.luckyfrog.quickmart.features.category.data.models.mapper

import com.luckyfrog.quickmart.features.category.data.models.response.CategoryResponseDto
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryEntity

// Mapping from DTO to Entity

fun CategoryResponseDto.toEntity() = CategoryEntity(
    id = this.id,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    createdBy = this.createdBy,
    updatedBy = this.updatedBy,
    name = this.name,
    description = this.description,
)

// Mapping from Entity to DTO
fun CategoryEntity.toDto() = CategoryResponseDto(
    id = this.id,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    createdBy = this.createdBy,
    updatedBy = this.updatedBy,
    name = this.name,
    description = this.description,
)
