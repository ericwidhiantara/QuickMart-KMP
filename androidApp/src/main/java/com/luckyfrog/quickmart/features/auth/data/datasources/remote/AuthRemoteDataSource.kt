package com.luckyfrog.quickmart.features.auth.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.UserResponseDto
import com.luckyfrog.quickmart.features.auth.domain.entities.RefreshTokenFormParamsEntity
import retrofit2.Response

interface AuthRemoteDataSource {
    suspend fun login(params: LoginFormRequestDto): Response<ResponseDto<LoginResponseDto>>

    suspend fun getUserLogin(): Response<ResponseDto<UserResponseDto>>

    suspend fun refreshToken(params: RefreshTokenFormParamsEntity): Response<ResponseDto<LoginResponseDto>>
}
