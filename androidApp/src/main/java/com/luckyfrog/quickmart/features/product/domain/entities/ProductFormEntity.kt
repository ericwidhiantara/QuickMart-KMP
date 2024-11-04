package com.luckyfrog.quickmart.features.product.domain.entities

import com.luckyfrog.quickmart.utils.helper.Constants

data class ProductFormParamsEntity(
    val limit: Int = Constants.MAX_PAGE_SIZE,
    val skip: Int = 0,
    val category: String?,
    val order: String = "asc",
    val sortBy: String = "createdAt",
    val q: String?,
)