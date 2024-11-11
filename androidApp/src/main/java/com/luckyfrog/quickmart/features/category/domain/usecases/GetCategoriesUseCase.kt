package com.luckyfrog.quickmart.features.category.domain.usecases

import com.luckyfrog.quickmart.core.generic.entities.PaginationEntity
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryEntity
import com.luckyfrog.quickmart.features.category.domain.repositories.CategoryRepository
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) : UseCase<Unit, Flow<ApiResponse<PaginationEntity<CategoryEntity>>>> {

    override suspend fun execute(input: Unit): Flow<ApiResponse<PaginationEntity<CategoryEntity>>> =
        flow {
            // Collect the response from repository
            repository.getCategories().collect { response ->
                when (response) {
                    is ApiResponse.Loading -> emit(ApiResponse.Loading)
                    is ApiResponse.Success -> {
                        // Extract UserEntity from ResponseDto
                        val userEntity = response.data.data
                        if (userEntity != null) {
                            emit(ApiResponse.Success(response.data.data))
                        } else {
                            emit(
                                ApiResponse.Failure(
                                    errorMessage = "Send OTP is null",
                                    code = 400 // Or any appropriate error code
                                )
                            )
                        }
                    }

                    is ApiResponse.Failure -> emit(
                        ApiResponse.Failure(
                            response.errorMessage,
                            response.code
                        )
                    )
                }
            }
        }
}