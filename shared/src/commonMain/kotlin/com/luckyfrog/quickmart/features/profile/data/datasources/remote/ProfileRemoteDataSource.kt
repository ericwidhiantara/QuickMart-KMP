package com.luckyfrog.quickmart.features.profile.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.profile.data.models.request.ChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.profile.data.models.request.CheckPasswordFormRequestDto

interface ProfileRemoteDataSource {

    suspend fun checkPassword(params: CheckPasswordFormRequestDto): ResponseDto<Unit>

    suspend fun changePassword(params: ChangePasswordFormRequestDto): ResponseDto<Unit>

}