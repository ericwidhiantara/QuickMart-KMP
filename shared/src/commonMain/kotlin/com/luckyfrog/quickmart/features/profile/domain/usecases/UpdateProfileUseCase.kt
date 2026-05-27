package com.luckyfrog.quickmart.features.profile.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.domain.entities.UserEntity
import com.luckyfrog.quickmart.features.profile.domain.entities.UpdateProfileParamsEntity
import com.luckyfrog.quickmart.features.profile.domain.repositories.ProfileRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import com.luckyfrog.quickmart.utils.UseCase
import kotlinx.coroutines.flow.Flow

class UpdateProfileUseCase(private val repository: ProfileRepository) :
    UseCase<UpdateProfileParamsEntity, Flow<ApiResponse<ResponseDto<UserEntity>>>> {
    override suspend fun execute(params: UpdateProfileParamsEntity) =
        repository.updateProfile(params)
}
