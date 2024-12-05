package com.luckyfrog.quickmart.features.profile.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.profile.data.models.request.CheckPasswordFormRequestDto
import com.luckyfrog.quickmart.features.profile.domain.repositories.ProfileRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class CheckPasswordUseCase(
    private val repository: ProfileRepository
) : UseCase<CheckPasswordFormRequestDto, Flow<ApiResponse<ResponseDto<Unit>>>> {
    override suspend fun execute(input: CheckPasswordFormRequestDto): Flow<ApiResponse<ResponseDto<Unit>>> {
        return repository.checkPassword(input)
    }
}
