package com.luckyfrog.quickmart.features.profile.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.profile.domain.repositories.ProfileRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import com.luckyfrog.quickmart.utils.UseCase
import kotlinx.coroutines.flow.Flow

class DeleteAccountUseCase(private val repository: ProfileRepository) :
    UseCase<Unit, Flow<ApiResponse<ResponseDto<Unit>>>> {
    override suspend fun execute(params: Unit) = repository.deleteAccount()
}
