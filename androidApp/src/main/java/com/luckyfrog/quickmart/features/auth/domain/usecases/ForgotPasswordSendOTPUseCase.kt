package com.luckyfrog.quickmart.features.auth.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordSendOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForgotPasswordSendOTPUseCase @Inject constructor(
    private val repository: AuthRepository
) : UseCase<ForgotPasswordSendOTPFormRequestDto, Flow<ApiResponse<ResponseDto<Unit>>>> {
    override suspend fun execute(input: ForgotPasswordSendOTPFormRequestDto): Flow<ApiResponse<ResponseDto<Unit>>> {
        return repository.forgotPasswordSendOTP(input)
    }
}