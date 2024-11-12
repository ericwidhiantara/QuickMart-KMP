package com.luckyfrog.quickmart.features.category.data.repositories

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.entities.PaginationEntity
import com.luckyfrog.quickmart.core.generic.mapper.toEntity
import com.luckyfrog.quickmart.core.network.processResponse
import com.luckyfrog.quickmart.features.category.data.datasources.remote.CategoryRemoteDataSource
import com.luckyfrog.quickmart.features.category.data.models.mapper.toEntity
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryEntity
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryFormParamsEntity
import com.luckyfrog.quickmart.features.category.domain.repositories.CategoryRepository
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val remoteDataSource: CategoryRemoteDataSource
) : CategoryRepository {

    override suspend fun getCategories(params: CategoryFormParamsEntity): Flow<ApiResponse<ResponseDto<PaginationEntity<CategoryEntity>>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.getCategories(params = params)
            emit(processResponse(response) { it ->
                it.data?.toEntity(
                    mapper = { it.toEntity() }
                )
            })
        }

}
