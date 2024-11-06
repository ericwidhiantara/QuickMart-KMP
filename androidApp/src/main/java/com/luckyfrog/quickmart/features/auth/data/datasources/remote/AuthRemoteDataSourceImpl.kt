package com.luckyfrog.quickmart.features.auth.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.UserResponseDto
import com.luckyfrog.quickmart.features.auth.domain.entities.CheckTokenFormParamsEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.RefreshTokenFormParamsEntity
import com.luckyfrog.quickmart.utils.TokenManager
import retrofit2.Response
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val api: AuthApi,
    private val tokenManager: TokenManager,
) : AuthRemoteDataSource {

    override suspend fun login(params: LoginFormRequestDto): Response<ResponseDto<LoginResponseDto>> {
        return api.postLogin(params)
    }

    override suspend fun getUserLogin(): Response<ResponseDto<UserResponseDto>> {
        val token = tokenManager.getToken()

        val params = CheckTokenFormParamsEntity(
            accessToken = token ?: ""
        )

        return api.getUserLogin(params)
    }

    override suspend fun refreshToken(params: RefreshTokenFormParamsEntity): Response<ResponseDto<LoginResponseDto>> {
        return api.postRefreshToken(params)
    }
}
