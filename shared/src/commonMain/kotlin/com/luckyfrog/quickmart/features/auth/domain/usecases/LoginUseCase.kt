package com.luckyfrog.quickmart.features.auth.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.auth.data.models.request.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.entities.AuthEntity
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class LoginUseCase(
    private val repository: AuthRepository
) : UseCase<LoginFormRequestDto, Flow<ApiResponse<ResponseDto<AuthEntity>>>> {

    override suspend fun execute(input: LoginFormRequestDto): Flow<ApiResponse<ResponseDto<AuthEntity>>> {
        return repository.login(input)
    }
}
