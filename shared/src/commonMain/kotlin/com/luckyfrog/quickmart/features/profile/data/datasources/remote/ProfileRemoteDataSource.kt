package com.luckyfrog.quickmart.features.profile.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.models.response.UserResponseDto
import com.luckyfrog.quickmart.features.profile.data.models.request.ChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.profile.data.models.request.CheckPasswordFormRequestDto
import com.luckyfrog.quickmart.features.profile.data.models.request.SendOTPFormRequestDto
import com.luckyfrog.quickmart.features.profile.data.models.request.VerifyOTPFormRequestDto

interface ProfileRemoteDataSource {
    suspend fun getUserLogin(): ResponseDto<UserResponseDto>

    suspend fun checkPassword(params: CheckPasswordFormRequestDto): ResponseDto<Unit>

    suspend fun changePassword(params: ChangePasswordFormRequestDto): ResponseDto<Unit>

    suspend fun sendOTP(params: SendOTPFormRequestDto): ResponseDto<Unit>

    suspend fun verifyOTP(params: VerifyOTPFormRequestDto): ResponseDto<Unit>

}
