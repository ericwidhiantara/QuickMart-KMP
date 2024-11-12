package com.luckyfrog.quickmart.features.category.domain.entities

import com.luckyfrog.quickmart.utils.helper.Constants

data class CategoryFormParamsEntity(
    val query: String?,
    val queryBy: String? = "name",
    val sortBy: String = "created_at",
    val sortOrder: String = "desc",
    val limit: Int = Constants.MAX_PAGE_SIZE,
    val page: Int = 1,
)