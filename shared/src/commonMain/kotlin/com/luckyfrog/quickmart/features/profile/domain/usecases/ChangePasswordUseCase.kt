package com.luckyfrog.quickmart.features.profile.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.profile.data.models.request.ChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.profile.domain.repositories.ProfileRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class ChangePasswordUseCase(
    private val repository: ProfileRepository
) : UseCase<ChangePasswordFormRequestDto, Flow<ApiResponse<ResponseDto<Unit>>>> {
    override suspend fun execute(input: ChangePasswordFormRequestDto): Flow<ApiResponse<ResponseDto<Unit>>> {
        return repository.changePassword(input)
    }
}
