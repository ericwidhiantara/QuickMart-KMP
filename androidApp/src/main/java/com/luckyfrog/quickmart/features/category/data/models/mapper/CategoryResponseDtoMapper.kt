package com.luckyfrog.quickmart.features.category.data.models.mapper

import com.luckyfrog.quickmart.features.category.data.models.response.CategoryResponseDto
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryEntity

// Mapping from DTO to Entity
fun CategoryResponseDto.toEntity() = CategoryEntity(
    slug = this.slug,
    name = this.name,
    url = this.url,
)

// Mapping from Entity to DTO
fun CategoryEntity.toDto() = CategoryResponseDto(
    slug = this.slug,
    name = this.name,
    url = this.url
)


fun List<CategoryResponseDto>.toEntityList(): List<CategoryEntity> {
    return this.map {
        it.toEntity()
    }
}

fun List<CategoryEntity>.toDtoList(): List<CategoryResponseDto> {
    return this.map {
        it.toDto()
    }
}