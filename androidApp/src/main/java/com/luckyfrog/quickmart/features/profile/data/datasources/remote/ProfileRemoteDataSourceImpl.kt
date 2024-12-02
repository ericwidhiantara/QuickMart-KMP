package com.luckyfrog.quickmart.features.profile.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.profile.data.models.request.ChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.profile.data.models.request.CheckPasswordFormRequestDto
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject

class ProfileRemoteDataSourceImpl @Inject constructor(
    private val api: ProfileApi,
) : ProfileRemoteDataSource {

    override suspend fun checkPassword(params: CheckPasswordFormRequestDto): Response<ResponseDto<Unit>> {
        return api.postCheckPassword(
            password = params.password.toRequestBody(),
        )
    }

    override suspend fun changePassword(params: ChangePasswordFormRequestDto): Response<ResponseDto<Unit>> {
        return api.postChangePassword(
            newPassword = params.newPassword.toRequestBody(),
            confirmPassword = params.confirmPassword.toRequestBody(),
        )
    }
}
