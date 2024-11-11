package com.luckyfrog.quickmart.features.category.domain.entities

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryEntity(
    val id: String?,
    @SerializedName("created_at")
    val createdAt: Int?,
    @SerializedName("updated_at")
    val updatedAt: Int?,
    @SerializedName("created_by")
    val createdBy: String?,
    @SerializedName("updated_by")
    val updatedBy: String?,
    val name: String?,
    val description: String? = null
)