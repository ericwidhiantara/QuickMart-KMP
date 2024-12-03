package com.luckyfrog.quickmart.features.auth.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordVerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.entities.ForgotPasswordVerifyCodeResponseEntity
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class ForgotPasswordVerifyOTPUseCase(
    private val repository: AuthRepository
) : UseCase<ForgotPasswordVerifyOTPFormRequestDto, Flow<ApiResponse<ResponseDto<ForgotPasswordVerifyCodeResponseEntity>>>> {
    override suspend fun execute(input: ForgotPasswordVerifyOTPFormRequestDto): Flow<ApiResponse<ResponseDto<ForgotPasswordVerifyCodeResponseEntity>>> {
        return repository.forgotPasswordVerifyOTP(input)
    }
}