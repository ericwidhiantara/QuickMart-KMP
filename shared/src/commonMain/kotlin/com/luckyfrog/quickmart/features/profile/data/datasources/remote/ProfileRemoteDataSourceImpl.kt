package com.luckyfrog.quickmart.features.profile.data.datasources.remote

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.profile.data.models.request.ChangePasswordFormRequestDto
import com.luckyfrog.quickmart.features.profile.data.models.request.CheckPasswordFormRequestDto

class ProfileRemoteDataSourceImpl(
    private val api: ProfileApi,
) : ProfileRemoteDataSource {

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
}
