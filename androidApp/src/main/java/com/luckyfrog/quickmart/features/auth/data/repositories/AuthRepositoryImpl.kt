package com.luckyfrog.quickmart.features.auth.data.repositories

import android.util.Log
import com.google.gson.Gson
import com.luckyfrog.quickmart.core.generic.dto.FailureResponse
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
import okhttp3.ResponseBody
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

        // First, check for the body content and process the response.
        response.body()?.let {
            val meta = it.meta
            val errorResponse = meta?.message

            // If the response is successful, map the data and return the success response.
            return if (response.isSuccessful) {
                ApiResponse.Success(ResponseDto(meta = meta, data = mapData(it)))
            } else {
                // If the response is not successful, parse and handle the error.
                ApiResponse.Failure(
                    errorMessage = errorResponse ?: "Unknown Error",
                    code = response.code()
                )
            }
        }

        // If the body is null, handle it by parsing the error message from the error body.
        response.errorBody()?.let { errorBody ->
            val errorMessage = parseErrorBody(errorBody)
            return ApiResponse.Failure(
                errorMessage = errorMessage ?: "Unknown Error",
                code = response.code()
            )
        }

        return ApiResponse.Failure("Unknown Error", response.code())
    }

    /**
     * Parse the error body and extract the message field from the meta part of the response.
     */
    private fun parseErrorBody(errorBody: ResponseBody): String? {
        val errorString = errorBody.string()
        return try {
            // Using Gson to parse the error body.
            val errorResponse = Gson().fromJson(errorString, FailureResponse::class.java)
            errorResponse.meta?.message // Extract the message from the meta
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Error parsing error body: ${e.message}")
            null
        }
    }
}
