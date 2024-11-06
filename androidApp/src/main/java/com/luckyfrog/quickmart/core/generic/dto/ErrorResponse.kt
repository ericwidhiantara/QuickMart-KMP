package com.luckyfrog.quickmart.core.generic.dto

data class ErrorResponse(
    val code: Int,
    val message: String
)

data class FailureResponse(
    val meta: MetaDto? = null,
)