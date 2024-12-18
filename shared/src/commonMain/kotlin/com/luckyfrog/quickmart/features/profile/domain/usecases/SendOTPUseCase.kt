package com.luckyfrog.quickmart.features.profile.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.profile.domain.repositories.ProfileRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class SendOTPUseCase(
    private val repository: ProfileRepository
) : UseCase<Unit, Flow<ApiResponse<ResponseDto<Unit>>>> {
    override suspend fun execute(input: Unit): Flow<ApiResponse<ResponseDto<Unit>>> {
        return repository.sendOTP()
    }
}