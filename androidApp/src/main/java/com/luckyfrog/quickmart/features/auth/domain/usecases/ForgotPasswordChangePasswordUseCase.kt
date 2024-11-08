package com.luckyfrog.quickmart.features.auth.domain.usecases

import com.luckyfrog.quickmart.core.generic.entities.MetaEntity
import com.luckyfrog.quickmart.core.generic.mapper.toEntity
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ForgotPasswordChangePasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) : UseCase<ForgotPasswordChangePasswordFormRequestDto, Flow<ApiResponse<MetaEntity>>> {

    override suspend fun execute(input: ForgotPasswordChangePasswordFormRequestDto): Flow<ApiResponse<MetaEntity>> =
        flow {
            // Collect the response from repository
            repository.forgotPasswordChangePassword(input).collect { response ->
                when (response) {
                    is ApiResponse.Loading -> emit(ApiResponse.Loading)
                    is ApiResponse.Success -> {
                        // Extract UserEntity from ResponseDto
                        val userEntity = response.data.data
                        if (userEntity != null) {
                            emit(ApiResponse.Success(response.data.meta?.toEntity()!!))
                        } else {
                            emit(
                                ApiResponse.Failure(
                                    errorMessage = "Change Password is null",
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
