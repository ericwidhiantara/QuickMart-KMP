package com.luckyfrog.quickmart.features.profile.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.profile.data.models.request.ChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.profile.data.models.request.CheckPasswordFormRequestDto
import retrofit2.Response

interface ProfileRemoteDataSource {

    suspend fun checkPassword(params: CheckPasswordFormRequestDto): Response<ResponseDto<Unit>>

    suspend fun changePassword(params: ChangePasswordFormRequestDto): Response<ResponseDto<Unit>>

}
