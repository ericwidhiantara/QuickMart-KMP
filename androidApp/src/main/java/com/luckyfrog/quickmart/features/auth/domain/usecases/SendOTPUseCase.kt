package com.luckyfrog.quickmart.features.auth.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendOTPUseCase @Inject constructor(
    private val repository: AuthRepository
) : UseCase<Unit, Flow<ApiResponse<ResponseDto<Unit>>>> {
    override suspend fun execute(input: Unit): Flow<ApiResponse<ResponseDto<Unit>>> {
        return repository.sendOTP()
    }
}