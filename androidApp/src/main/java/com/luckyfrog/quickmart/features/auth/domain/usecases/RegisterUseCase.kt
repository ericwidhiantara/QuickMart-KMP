package com.luckyfrog.quickmart.features.auth.domain.usecases

import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.auth.data.models.request.RegisterFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.entities.AuthEntity
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) : UseCase<RegisterFormRequestDto, Flow<ApiResponse<AuthEntity>>> {

    override suspend fun execute(input: RegisterFormRequestDto): Flow<ApiResponse<AuthEntity>> =
        flow {
            // Collect the response from repository
            repository.register(input).collect { response ->
                when (response) {
                    is ApiResponse.Loading -> emit(ApiResponse.Loading)
                    is ApiResponse.Success -> {
                        // Extract AuthEntity from ResponseDto
                        val entity = response.data.data
                        if (entity != null) {
                            emit(ApiResponse.Success(entity))
                        } else {
                            emit(
                                ApiResponse.Failure(
                                    errorMessage = "Register data is null",
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
