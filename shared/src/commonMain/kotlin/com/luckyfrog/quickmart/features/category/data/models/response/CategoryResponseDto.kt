package com.luckyfrog.quickmart.features.category.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponseDto(
    val id: String?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("updated_at")
    val updatedAt: String?,
    @SerialName("created_by")
    val createdBy: String?,
    @SerialName("updated_by")
    val updatedBy: String?,
    val name: String?,
    val description: String? = null
)