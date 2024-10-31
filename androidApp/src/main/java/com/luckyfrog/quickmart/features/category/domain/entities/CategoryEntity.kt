package com.luckyfrog.quickmart.features.category.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class CategoryEntity(
    val slug: String?,
    val name: String?,
    val url: String?,
)
