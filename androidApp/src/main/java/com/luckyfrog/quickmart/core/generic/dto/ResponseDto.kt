package com.luckyfrog.quickmart.core.generic.dto

import com.google.gson.annotations.SerializedName

data class ResponseDto<T>(
    @SerializedName("meta")
    val meta: MetaDto? = null,

    @SerializedName("data")
    val data: T? = null
)

data class MetaDto(
    @SerializedName("code")
    val code: Int? = null,

    @SerializedName("error")
    val error: Boolean = false,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("error_detail")
    val errorDetail: String? = null
)
