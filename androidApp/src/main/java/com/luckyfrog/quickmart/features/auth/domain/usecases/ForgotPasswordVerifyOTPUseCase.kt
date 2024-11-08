package com.luckyfrog.quickmart.features.auth.domain.usecases

import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordVerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.entities.ForgotPasswordVerifyCodeResponseEntity
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ForgotPasswordVerifyOTPUseCase @Inject constructor(
    private val repository: AuthRepository
) : UseCase<ForgotPasswordVerifyOTPFormRequestDto, Flow<ApiResponse<ForgotPasswordVerifyCodeResponseEntity>>> {

    override suspend fun execute(input: ForgotPasswordVerifyOTPFormRequestDto): Flow<ApiResponse<ForgotPasswordVerifyCodeResponseEntity>> =
        flow {
            // Collect the response from repository
            repository.forgotPasswordVerifyOTP(input).collect { response ->
                when (response) {
                    is ApiResponse.Loading -> emit(ApiResponse.Loading)
                    is ApiResponse.Success -> {
                        // Extract UserEntity from ResponseDto
                        val entity = response.data.data
                        if (entity != null) {
                            emit(ApiResponse.Success(entity))
                        } else {
                            emit(
                                ApiResponse.Failure(
                                    errorMessage = "Verify OTP is null",
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
