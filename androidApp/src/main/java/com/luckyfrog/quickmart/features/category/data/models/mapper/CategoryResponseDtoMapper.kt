package com.luckyfrog.quickmart.features.category.data.models.mapper

import com.luckyfrog.quickmart.features.category.data.models.response.CategoryItemResponseDto
import com.luckyfrog.quickmart.features.category.data.models.response.CategoryResponseDto
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryEntity
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryItemEntity

// Mapping from DTO to Entity
fun CategoryResponseDto.toEntity() = CategoryEntity(
    total = this.total,
    pageTotal = this.pageTotal,
    currentPage = this.currentPage,
    pageNumList = this.pageNumList,
    data = this.data?.toEntityList(),
)

fun CategoryItemResponseDto.toEntity() = CategoryItemEntity(
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
    total = this.total,
    pageTotal = this.pageTotal,
    currentPage = this.currentPage,
    pageNumList = this.pageNumList,
    data = this.data?.toDtoList(),
)

fun CategoryItemEntity.toDto() = CategoryItemResponseDto(
    id = this.id,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    createdBy = this.createdBy,
    updatedBy = this.updatedBy,
    name = this.name,
    description = this.description,
)


fun List<CategoryItemResponseDto>.toEntityList(): List<CategoryItemEntity> {
    return this.map {
        it.toEntity()
    }
}

fun List<CategoryItemEntity>.toDtoList(): List<CategoryItemResponseDto> {
    return this.map {
        it.toDto()
    }
}