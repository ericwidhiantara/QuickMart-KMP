package com.luckyfrog.quickmart.features.product.domain.entities

import com.luckyfrog.quickmart.utils.Constants

data class ProductFormParamsEntity(
    val categoryId: String?,
    val query: String?,
    val queryBy: String? = "name",
    val sortBy: String = "created_at",
    val sortOrder: String = "desc",
    val limit: Int = Constants.MAX_PAGE_SIZE,
    val page: Int = 1,
)