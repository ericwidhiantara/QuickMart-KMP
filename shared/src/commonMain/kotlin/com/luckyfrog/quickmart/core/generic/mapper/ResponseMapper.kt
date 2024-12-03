package com.luckyfrog.quickmart.core.generic.mapper

import com.luckyfrog.quickmart.core.generic.dto.MetaDto
import com.luckyfrog.quickmart.core.generic.dto.PaginationDto
import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.entities.MetaEntity
import com.luckyfrog.quickmart.core.generic.entities.PaginationEntity
import com.luckyfrog.quickmart.core.generic.entities.ResponseEntity

fun <T> ResponseDto<T>.toEntity(): ResponseEntity<T> {
    return ResponseEntity(
        meta = this.meta?.toEntity(),
        data = this.data
    )
}

fun MetaDto.toEntity(): MetaEntity {
    return MetaEntity(
        code = this.code,
        error = this.error,
        message = this.message,
        errorDetail = this.errorDetail
    )
}

fun <T, R> List<T>.toEntityList(mapper: (T) -> R): List<R> {
    return this.map { mapper(it) }
}


fun <T, R> ResponseDto<PaginationDto<T>>.toEntity(mapper: (T) -> R): ResponseEntity<PaginationEntity<R>> {
    return ResponseEntity(
        meta = this.meta?.toEntity(),
        data = this.data?.toEntity(mapper)
    )
}

fun <T, R> PaginationDto<T>.toEntity(mapper: (T) -> R): PaginationEntity<R> {
    return PaginationEntity(
        total = this.total,
        pageTotal = this.pageTotal,
        currentPage = this.currentPage,
        pageNumList = this.pageNumList,
        data = this.data?.map(mapper) ?: emptyList()
    )
}


fun <T> List<T>.toDtoList(): List<T> {
    return this.map {
        it
    }
}