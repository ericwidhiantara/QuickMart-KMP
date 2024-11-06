package com.luckyfrog.quickmart.features.auth.domain.usecases

import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.entities.LoginEntity
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) : UseCase<LoginFormRequestDto, Flow<ApiResponse<LoginEntity>>> {

    override suspend fun execute(input: LoginFormRequestDto): Flow<ApiResponse<LoginEntity>> =
        flow {
            // Collect the response from repository
            repository.login(input).collect { response ->
                when (response) {
                    is ApiResponse.Loading -> emit(ApiResponse.Loading)
                    is ApiResponse.Success -> {
                        // Extract LoginEntity from ResponseDto
                        val loginEntity = response.data.data
                        if (loginEntity != null) {
                            emit(ApiResponse.Success(loginEntity))
                        } else {
                            emit(
                                ApiResponse.Failure(
                                    errorMessage = "Login data is null",
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
