package com.luckyfrog.quickmart.features.category.domain.repositories

import com.luckyfrog.quickmart.core.generic.entities.PaginationEntity
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryEntity

interface CategoryRepository {

    suspend fun getCategories(): PaginationEntity<CategoryEntity>

}