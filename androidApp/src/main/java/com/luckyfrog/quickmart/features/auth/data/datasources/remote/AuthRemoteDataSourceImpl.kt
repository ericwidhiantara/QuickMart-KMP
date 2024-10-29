package com.luckyfrog.quickmart.features.auth.data.datasources.remote

import com.luckyfrog.quickmart.features.auth.data.models.response.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.RefreshTokenFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.UserResponseDto
import retrofit2.Response
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val api: AuthApi
) : AuthRemoteDataSource {

    override suspend fun login(params: LoginFormRequestDto): Response<LoginResponseDto> {
        return api.postLogin(params)
    }

    override suspend fun getUserLogin(): Response<UserResponseDto> {
        return api.getUserLogin()
    }


    override suspend fun refreshToken(params: RefreshTokenFormRequestDto): Response<LoginResponseDto> {
        return api.postRefreshToken(params)
    }
}