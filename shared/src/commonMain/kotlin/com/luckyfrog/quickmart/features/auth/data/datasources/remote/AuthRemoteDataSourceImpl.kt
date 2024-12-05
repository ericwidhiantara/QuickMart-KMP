package com.luckyfrog.quickmart.features.auth.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.preferences.StringSettingConfig
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordSendOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordVerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.RegisterFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.VerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.response.AuthResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.ForgotPasswordVerifyCodeResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.UserResponseDto
import com.russhwolf.settings.Settings
import io.ktor.client.statement.HttpResponse

class AuthRemoteDataSourceImpl(
    private val api: AuthApi, // Use AuthApi instead of HttpClient
    settings: Settings,

) : AuthRemoteDataSource {
    private val _token = StringSettingConfig(settings, "token", "")
    override suspend fun login(params: LoginFormRequestDto): ResponseDto<AuthResponseDto> {
        return api.postLogin(params.username, params.password)
    }

    override suspend fun register(params: RegisterFormRequestDto): ResponseDto<AuthResponseDto> {
        return api.postRegister(
            fullname = params.fullname,
            username = params.username,
            email = params.email,
            password = params.password,
            confirmPassword = params.confirmPassword
        )
    }

    override suspend fun sendOTP(): ResponseDto<Unit> {
        return api.postSendOTP()
    }

    override suspend fun verifyOTP(params: VerifyOTPFormRequestDto):  ResponseDto<Unit> {
        return api.postVerifyOTP(params.otpCode)
    }

    override suspend fun forgotPasswordSendOTP(params: ForgotPasswordSendOTPFormRequestDto): ResponseDto<Unit> {
        return api.postForgotPasswordSendOTP(params.email)
    }

    override suspend fun forgotPasswordVerifyOTP(params: ForgotPasswordVerifyOTPFormRequestDto): ResponseDto<ForgotPasswordVerifyCodeResponseDto> {
        return api.postForgotPasswordVerifyOTP(
            email = params.email,
            otpCode = params.otpCode
        )
    }

    override suspend fun forgotPasswordChangePassword(params: ForgotPasswordChangePasswordFormRequestDto): ResponseDto<Unit> {
        return api.postForgotPasswordChangePassword(
            otpId = params.otpId,
            newPassword = params.newPassword,
            confirmPassword = params.confirmPassword
        )
    }

    override suspend fun getUserLogin(): ResponseDto<UserResponseDto> {
        val token = _token.get()
        return api.getUserLogin(accessToken = token)
    }
}