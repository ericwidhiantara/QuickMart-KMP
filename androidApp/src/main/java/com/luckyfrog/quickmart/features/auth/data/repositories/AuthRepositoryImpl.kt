package com.luckyfrog.quickmart.features.auth.data.repositories

import android.util.Log
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthRemoteDataSource
import com.luckyfrog.quickmart.features.auth.data.models.mapper.toEntity
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.entities.LoginEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.UserEntity
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import com.luckyfrog.quickmart.utils.helper.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun login(params: LoginFormRequestDto): Flow<ApiResponse<LoginEntity>> =
        apiRequestFlow {
            remoteDataSource.login(params)
                .let { response ->
                    response.body()?.toEntity()?.let { entity ->
                        Response.success(entity)
                    } ?: Response.error(response.code(), response.errorBody()!!)
                }
        }

    override suspend fun getUserLogin(): Flow<ApiResponse<UserEntity>> = apiRequestFlow {
        remoteDataSource.getUserLogin().let { response ->
            Log.d("AuthRepositoryImpl", "response: ${response}")
            response.body()?.toEntity()?.let { entity ->
                Response.success(entity)
            } ?: Response.error(response.code(), response.errorBody()!!)
        }

    }

}