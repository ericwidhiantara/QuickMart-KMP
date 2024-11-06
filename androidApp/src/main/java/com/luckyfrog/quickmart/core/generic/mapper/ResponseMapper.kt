package com.luckyfrog.quickmart.core.generic.mapper

import com.luckyfrog.quickmart.core.generic.dto.MetaDto
import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.entities.MetaEntity
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
