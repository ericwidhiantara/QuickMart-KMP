package com.luckyfrog.quickmart.features.category.data.models.response

import com.google.gson.annotations.SerializedName

data class CategoryResponseDto(
    val id: String?,
    @SerializedName("created_at")
    val createdAt: Int?,
    @SerializedName("updated_at")
    val updatedAt: Int?,
    @SerializedName("created_by")
    val createdBy: Int?,
    @SerializedName("updated_by")
    val updatedBy: Int?,
    val name: String?,
    val description: String? = null
)