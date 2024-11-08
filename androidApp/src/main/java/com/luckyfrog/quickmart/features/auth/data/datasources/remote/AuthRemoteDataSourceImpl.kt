package com.luckyfrog.quickmart.features.auth.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.AuthResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.RegisterFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.UserResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.VerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.entities.CheckTokenFormParamsEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.RefreshTokenFormParamsEntity
import com.luckyfrog.quickmart.utils.TokenManager
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val api: AuthApi,
    private val tokenManager: TokenManager,
) : AuthRemoteDataSource {

    override suspend fun login(params: LoginFormRequestDto): Response<ResponseDto<AuthResponseDto>> {
        return api.postLogin(
            username = params.username.toRequestBody(),
            password = params.password.toRequestBody(),
        )
    }

    override suspend fun register(params: RegisterFormRequestDto): Response<ResponseDto<AuthResponseDto>> {
        return api.postRegister(
            fullname = params.fullname.toRequestBody(),
            username = params.username.toRequestBody(),
            email = params.email.toRequestBody(),
            password = params.password.toRequestBody(),
            confirmPassword = params.confirmPassword.toRequestBody(),
        )
    }

    override suspend fun sendOTP(): Response<ResponseDto<Unit>> {
        return api.postSendOTP()
    }

    override suspend fun verifyOTP(params: VerifyOTPFormRequestDto): Response<ResponseDto<Unit>> {
        return api.postVerifyOTP(
            otpCode = params.otpCode.toRequestBody(),
        )
    }

    override suspend fun getUserLogin(): Response<ResponseDto<UserResponseDto>> {
        val token = tokenManager.getToken()

        val params = CheckTokenFormParamsEntity(
            accessToken = token ?: ""
        )

        return api.getUserLogin(params.accessToken.toRequestBody())
    }

    override suspend fun refreshToken(params: RefreshTokenFormParamsEntity): Response<ResponseDto<AuthResponseDto>> {
        return api.postRefreshToken(params.refreshToken.toRequestBody())
    }
}
