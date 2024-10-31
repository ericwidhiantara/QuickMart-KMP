package com.luckyfrog.quickmart.features.category.data.models.response

import com.google.gson.annotations.SerializedName

data class CategoryResponseDto(
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?,
)
