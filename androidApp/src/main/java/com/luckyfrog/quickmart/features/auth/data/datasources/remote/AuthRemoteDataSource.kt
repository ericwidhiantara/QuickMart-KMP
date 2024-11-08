package com.luckyfrog.quickmart.features.auth.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.AuthResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.ForgotPasswordSendOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.RegisterFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.UserResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.VerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.entities.RefreshTokenFormParamsEntity
import retrofit2.Response

interface AuthRemoteDataSource {
    suspend fun login(params: LoginFormRequestDto): Response<ResponseDto<AuthResponseDto>>

    suspend fun register(params: RegisterFormRequestDto): Response<ResponseDto<AuthResponseDto>>

    suspend fun sendOTP(): Response<ResponseDto<Unit>>

    suspend fun verifyOTP(params: VerifyOTPFormRequestDto): Response<ResponseDto<Unit>>

    suspend fun forgotPasswordSendOTP(params: ForgotPasswordSendOTPFormRequestDto): Response<ResponseDto<Unit>>

    suspend fun getUserLogin(): Response<ResponseDto<UserResponseDto>>

    suspend fun refreshToken(params: RefreshTokenFormParamsEntity): Response<ResponseDto<AuthResponseDto>>
}
