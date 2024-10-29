package com.luckyfrog.quickmart.features.auth.data.datasources.remote

import com.luckyfrog.quickmart.features.auth.data.models.response.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.RefreshTokenFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.UserResponseDto
import retrofit2.Response

interface AuthRemoteDataSource {
    suspend fun login(params: LoginFormRequestDto): Response<LoginResponseDto>

    suspend fun getUserLogin(): Response<UserResponseDto>

    suspend fun refreshToken(params: RefreshTokenFormRequestDto): Response<LoginResponseDto>

}
