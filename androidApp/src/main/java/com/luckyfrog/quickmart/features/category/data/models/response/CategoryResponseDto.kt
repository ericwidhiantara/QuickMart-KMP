package com.luckyfrog.quickmart.features.category.data.models.response

import com.google.gson.annotations.SerializedName

data class CategoryResponseDto(
    val total: Int?,
    @SerializedName("current_page")
    val currentPage: Int?,
    @SerializedName("page_total")
    val pageTotal: Int?,
    @SerializedName("page_num_list")
    val pageNumList: List<Long>?,
    val data: List<CategoryItemResponseDto>?
)

data class CategoryItemResponseDto(
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