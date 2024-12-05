package com.luckyfrog.quickmart.core.network

import com.luckyfrog.quickmart.core.generic.dto.MetaDto
import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.utils.ApiResponse

/**
 * Helper function to process the API response and map it to ApiResponse.
 */
suspend inline fun <T, R> processResponse(
    response: ResponseDto<T>,
    mapData: (ResponseDto<T>) -> R?
): ApiResponse<ResponseDto<R>> {
    return try {
        println("processResponse response: $response")
        val res: ResponseDto<T> = response

        // Check if the response is successful
        if (res.meta?.code == 200) {
            ApiResponse.Success(ResponseDto(meta = res.meta, data = mapData(res)))
        } else {
            // Parse the error response if the status is not successful
            val errorMessage = parseErrorBody(ResponseDto(meta = res.meta))
            ApiResponse.Failure(
                errorMessage = errorMessage ?: "Unknown Error",
                code = response.meta?.code ?: 500
            )
        }
    } catch (e: Exception) {
        ApiResponse.Failure("An error occurred: ${e.message}", response.meta?.code ?: 500)
    }
}

/**
 * Parse the error body and extract the message field from the meta part of the response.
 */
suspend fun parseErrorBody(response: ResponseDto<MetaDto>): String? {
    return try {
        response.meta?.message // Extract the message from the meta
    } catch (e: Exception) {
        null
    }
}
