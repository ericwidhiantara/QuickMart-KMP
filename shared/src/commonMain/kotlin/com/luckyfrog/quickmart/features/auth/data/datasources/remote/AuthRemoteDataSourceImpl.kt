package com.luckyfrog.quickmart.features.auth.data.datasources.remote

import com.luckyfrog.quickmart.core.preferences.StringSettingConfig
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordSendOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordVerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.RegisterFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.VerifyOTPFormRequestDto
import com.russhwolf.settings.Settings
import io.ktor.client.statement.HttpResponse

class AuthRemoteDataSourceImpl(
    private val api: AuthApi, // Use AuthApi instead of HttpClient
    settings: Settings,

) : AuthRemoteDataSource {
    private val _token = StringSettingConfig(settings, "token", "")
    override suspend fun login(params: LoginFormRequestDto): HttpResponse {
        return api.postLogin(params.username, params.password)
    }

    override suspend fun register(params: RegisterFormRequestDto): HttpResponse {
        return api.postRegister(
            fullname = params.fullname,
            username = params.username,
            email = params.email,
            password = params.password,
            confirmPassword = params.confirmPassword
        )
    }

    override suspend fun sendOTP(): HttpResponse {
        return api.postSendOTP()
    }

    override suspend fun verifyOTP(params: VerifyOTPFormRequestDto): HttpResponse {
        return api.postVerifyOTP(params.otpCode)
    }

    override suspend fun forgotPasswordSendOTP(params: ForgotPasswordSendOTPFormRequestDto): HttpResponse {
        return api.postForgotPasswordSendOTP(params.email)
    }

    override suspend fun forgotPasswordVerifyOTP(params: ForgotPasswordVerifyOTPFormRequestDto): HttpResponse {
        return api.postForgotPasswordVerifyOTP(
            email = params.email,
            otpCode = params.otpCode
        )
    }

    override suspend fun forgotPasswordChangePassword(params: ForgotPasswordChangePasswordFormRequestDto): HttpResponse {
        return api.postForgotPasswordChangePassword(
            otpId = params.otpId,
            newPassword = params.newPassword,
            confirmPassword = params.confirmPassword
        )
    }

    override suspend fun getUserLogin(): HttpResponse {
        val token = _token.get()
        return api.getUserLogin(accessToken = token)
    }
}
