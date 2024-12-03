package com.luckyfrog.quickmart.features.auth.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.auth.data.models.request.VerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class VerifyOTPUseCase(
    private val repository: AuthRepository
) : UseCase<VerifyOTPFormRequestDto, Flow<ApiResponse<ResponseDto<Unit>>>> {
    override suspend fun execute(input: VerifyOTPFormRequestDto): Flow<ApiResponse<ResponseDto<Unit>>> {
        return repository.verifyOTP(input)
    }
}