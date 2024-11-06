package com.luckyfrog.quickmart.features.auth.domain.repositories

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.entities.LoginEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.UserEntity
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(params: LoginFormRequestDto): Flow<ApiResponse<ResponseDto<LoginEntity>>>

    suspend fun getUserLogin(): Flow<ApiResponse<ResponseDto<UserEntity>>>
}
