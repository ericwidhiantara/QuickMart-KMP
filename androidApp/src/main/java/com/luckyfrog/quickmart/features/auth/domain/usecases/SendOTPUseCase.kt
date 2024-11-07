package com.luckyfrog.quickmart.features.auth.domain.usecases

import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendOTPUseCase @Inject constructor(
    private val repository: AuthRepository
) : UseCase<Unit, Flow<ApiResponse<Unit>>> {

    override suspend fun execute(input: Unit): Flow<ApiResponse<Unit>> =
        flow {
            // Collect the response from repository
            repository.sendOTP().collect { response ->
                when (response) {
                    is ApiResponse.Loading -> emit(ApiResponse.Loading)
                    is ApiResponse.Success -> {
                        // Extract UserEntity from ResponseDto
                        val userEntity = response.data.data
                        if (userEntity != null) {
                            emit(ApiResponse.Success(Unit))
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
