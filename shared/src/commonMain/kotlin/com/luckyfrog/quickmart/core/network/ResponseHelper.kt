package com.luckyfrog.quickmart.core.network

import com.luckyfrog.quickmart.core.generic.dto.FailureResponse
import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.utils.ApiResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json

/**
 * Helper function to process the API response and map it to ApiResponse.
 */
suspend inline fun <T, R> processResponse(
    response: HttpResponse,
    mapData: (ResponseDto<T>) -> R?
): ApiResponse<ResponseDto<R>> {
    return try {
        // Check if the response is successful
        if (response.status.isSuccess()) {
            val body: ResponseDto<T> = response.body()
            ApiResponse.Success(ResponseDto(meta = body.meta, data = mapData(body)))
        } else {
            // Parse the error response if the status is not successful
            val errorMessage = parseErrorBody(response)
            ApiResponse.Failure(
                errorMessage = errorMessage ?: "Unknown Error",
                code = response.status.value
            )
        }
    } catch (e: Exception) {
        ApiResponse.Failure("An error occurred: ${e.message}", response.status.value)
    }
}

/**
 * Parse the error body and extract the message field from the meta part of the response.
 */
suspend fun parseErrorBody(response: HttpResponse): String? {
    return try {
        val errorBody = response.bodyAsText()
        val errorResponse = Json.decodeFromString<FailureResponse>(errorBody)
        errorResponse.meta?.message // Extract the message from the meta
    } catch (e: Exception) {
        null
    }
}
