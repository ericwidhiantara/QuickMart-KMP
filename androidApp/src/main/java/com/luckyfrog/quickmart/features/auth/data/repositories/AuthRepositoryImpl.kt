package com.luckyfrog.quickmart.features.auth.data.repositories

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.features.auth.data.datasources.remote.AuthRemoteDataSource
import com.luckyfrog.quickmart.features.auth.data.models.mapper.toEntity
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginFormRequestDto
import com.luckyfrog.quickmart.features.auth.domain.entities.LoginEntity
import com.luckyfrog.quickmart.features.auth.domain.entities.UserEntity
import com.luckyfrog.quickmart.features.auth.domain.repositories.AuthRepository
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun login(params: LoginFormRequestDto): Flow<ApiResponse<ResponseDto<LoginEntity>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = remoteDataSource.login(params)
            emit(processResponse(response) { it.data?.toEntity() })
        }

    override suspend fun getUserLogin(): Flow<ApiResponse<ResponseDto<UserEntity>>> = flow {
        emit(ApiResponse.Loading)
        val response = remoteDataSource.getUserLogin()
        emit(processResponse(response) { it.data?.toEntity() })
    }

    /**
     * Helper function to process the API response and map it to ApiResponse.
     */
    private inline fun <T, R> processResponse(
        response: Response<ResponseDto<T>>,
        mapData: (ResponseDto<T>) -> R?
    ): ApiResponse<ResponseDto<R>> {
        return if (response.isSuccessful) {
            response.body()?.let { dto ->
                val mappedData = mapData(dto)
                ApiResponse.Success(ResponseDto(meta = dto.meta, data = mappedData))
            } ?: ApiResponse.Failure("Empty response body", response.code())
        } else {
            ApiResponse.Failure(
                errorMessage = response.errorBody()?.string() ?: "Unknown Error",
                code = response.code()
            )
        }
    }
}
