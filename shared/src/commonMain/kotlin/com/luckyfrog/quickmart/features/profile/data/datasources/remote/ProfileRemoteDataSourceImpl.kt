package com.luckyfrog.quickmart.features.profile.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.UserResponseDto
import com.luckyfrog.quickmart.features.profile.data.models.request.ChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.profile.data.models.request.CheckPasswordFormRequestDto
import com.luckyfrog.quickmart.features.profile.data.models.request.VerifyOTPFormRequestDto

class ProfileRemoteDataSourceImpl(
    private val api: ProfileApi,
) : ProfileRemoteDataSource {
    override suspend fun getUserLogin(): ResponseDto<UserResponseDto> = api.getUserLogin()

    override suspend fun checkPassword(params: CheckPasswordFormRequestDto): ResponseDto<Unit> =
        api.postCheckPassword(password = params.password)

    override suspend fun changePassword(params: ChangePasswordFormRequestDto): ResponseDto<Unit> =
        api.postChangePassword(newPassword = params.newPassword, confirmPassword = params.confirmPassword)

    override suspend fun sendOTP(): ResponseDto<Unit> = api.postSendOTP()

    override suspend fun verifyOTP(params: VerifyOTPFormRequestDto): ResponseDto<Unit> =
        api.postVerifyOTP(params.otpCode)

    override suspend fun updateProfile(
        fullname: String?, username: String?, email: String?,
        phoneNumber: String?, gender: String?, birthDate: String?,
        language: String?, currency: String?
    ): ResponseDto<UserResponseDto> =
        api.updateProfile(fullname, username, email, phoneNumber, gender, birthDate, language, currency)

    override suspend fun deleteAccount(): ResponseDto<Unit> = api.deleteAccount()
}
