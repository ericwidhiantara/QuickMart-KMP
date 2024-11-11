package com.luckyfrog.quickmart.core.generic.entities

data class ResponseEntity<T>(
    val meta: MetaEntity?,
    val data: T?
)

data class MetaEntity(
    val code: Int?,
    val error: Boolean,
    val message: String?,
    val errorDetail: Any?
)


data class PaginationEntity<T>(
    val total: Int?,
    val currentPage: Int?,
    val pageTotal: Int?,
    val pageNumList: List<Long>?,
    val data: List<T>?
)