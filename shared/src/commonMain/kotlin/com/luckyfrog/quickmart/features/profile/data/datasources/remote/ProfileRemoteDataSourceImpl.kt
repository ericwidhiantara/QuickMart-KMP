package com.luckyfrog.quickmart.features.profile.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.UserResponseDto
import com.luckyfrog.quickmart.features.profile.data.models.request.ChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.profile.data.models.request.CheckPasswordFormRequestDto
import com.luckyfrog.quickmart.features.profile.data.models.request.SendOTPFormRequestDto
import com.luckyfrog.quickmart.features.profile.data.models.request.VerifyOTPFormRequestDto

class ProfileRemoteDataSourceImpl(
    private val api: ProfileApi,
) : ProfileRemoteDataSource {
    override suspend fun getUserLogin(): ResponseDto<UserResponseDto> {
        return api.getUserLogin()
    }

    override suspend fun checkPassword(params: CheckPasswordFormRequestDto): ResponseDto<Unit> {
        return api.postCheckPassword(
            password = params.password,
        )
    }

    override suspend fun changePassword(params: ChangePasswordFormRequestDto): ResponseDto<Unit> {
        return api.postChangePassword(
            newPassword = params.newPassword,
            confirmPassword = params.confirmPassword,
        )
    }

    override suspend fun sendOTP(params: SendOTPFormRequestDto): ResponseDto<Unit> {
        return api.postSendOTP(
            token = params.token,
        )
    }

    override suspend fun verifyOTP(params: VerifyOTPFormRequestDto): ResponseDto<Unit> {
        return api.postVerifyOTP(params.otpCode)
    }
}
