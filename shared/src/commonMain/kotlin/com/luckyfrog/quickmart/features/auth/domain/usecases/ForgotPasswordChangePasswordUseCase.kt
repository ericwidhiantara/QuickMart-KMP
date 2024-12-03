package com.luckyfrog.quickmart.features.auth.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class ForgotPasswordChangePasswordUseCase(
    private val repository: AuthRepository
) : UseCase<ForgotPasswordChangePasswordFormRequestDto, Flow<ApiResponse<ResponseDto<Unit>>>> {
    override suspend fun execute(input: ForgotPasswordChangePasswordFormRequestDto): Flow<ApiResponse<ResponseDto<Unit>>> {
        return repository.forgotPasswordChangePassword(input)
    }
}
