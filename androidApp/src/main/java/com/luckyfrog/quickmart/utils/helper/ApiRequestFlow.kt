package com.luckyfrog.quickmart.utils.helper

import android.util.Log
import com.google.gson.Gson
import com.luckyfrog.quickmart.core.generic.dto.ErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.Response

fun <T> apiRequestFlow(call: suspend () -> Response<T>): Flow<ApiResponse<T>> = flow {
    emit(ApiResponse.Loading)

    withTimeoutOrNull(20000L) {
        val response = call()

        try {
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    emit(ApiResponse.Success(data))
                }
            } else {
                response.errorBody()?.let { error ->
                    val rawError = error.string() // Read the raw error response
                    Log.d("ApiRequestFlow", "Raw error response: $rawError")

                    val parsedError: ErrorResponse =
                        Gson().fromJson(rawError, ErrorResponse::class.java)

                    error.close() // Close the error body after reading

                    // Fallback to HTTP response code if parsed error code is 0
                    val errorCode = parsedError.code.takeIf { it != 0 } ?: response.code()

                    emit(ApiResponse.Failure(parsedError.message, errorCode))
                }
            }
        } catch (e: Exception) {
            Log.e("ApiRequestFlow", "Exception occurred: ${e.message}")
            emit(ApiResponse.Failure(e.message ?: e.toString(), 400))
        }
    } ?: emit(ApiResponse.Failure("Timeout! Please try again.", 408))
}.flowOn(Dispatchers.IO)
