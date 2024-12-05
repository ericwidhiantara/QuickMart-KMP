package com.luckyfrog.quickmart.features.profile.domain.repositories

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.profile.data.models.request.ChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.profile.data.models.request.CheckPasswordFormRequestDto
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun checkPassword(params: CheckPasswordFormRequestDto): Flow<ApiResponse<ResponseDto<Unit>>>

    suspend fun changePassword(params: ChangePasswordFormRequestDto): Flow<ApiResponse<ResponseDto<Unit>>>

}
