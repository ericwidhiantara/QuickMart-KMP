package com.luckyfrog.quickmart.features.category.data.repositories

import com.luckyfrog.quickmart.core.generic.entities.PaginationEntity
import com.luckyfrog.quickmart.core.generic.mapper.toEntity
import com.luckyfrog.quickmart.features.category.data.datasources.remote.CategoryRemoteDataSource
import com.luckyfrog.quickmart.features.category.data.models.mapper.toEntity
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryEntity
import com.luckyfrog.quickmart.features.category.domain.repositories.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val remoteDataSource: CategoryRemoteDataSource
) : CategoryRepository {

    override suspend fun getCategories(): PaginationEntity<CategoryEntity> {
        return remoteDataSource.getCategories().toEntity(
            mapper = { it.toEntity() }
        )
    }
}