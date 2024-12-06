package com.luckyfrog.quickmart.core.generic.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto<T>(
    @SerialName("meta")
    val meta: MetaDto? = null,

    @SerialName("data")
    val data: T? = null
)

@Serializable
data class MetaDto(
    @SerialName("code")
    val code: Int? = null,

    @SerialName("error")
    val error: Boolean = false,

    @SerialName("message")
    val message: String? = null,

    @SerialName("error_detail")
    val errorDetail: String? = null
)

@Serializable
data class PaginationDto<T>(
    val total: Int?,
    @SerialName("current_page")
    val currentPage: Int?,
    @SerialName("page_total")
    val pageTotal: Int?,
    @SerialName("page_num_list")
    val pageNumList: List<Long>?,
    val data: List<T>?
)