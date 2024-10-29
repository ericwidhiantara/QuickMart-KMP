package com.luckyfrog.quickmart.core.generic.dto

import com.google.gson.annotations.SerializedName

class ResponseDto<T : Any?> {
    @SerializedName("products")
    val products: T? = null

    @SerializedName("total")
    val total: Int? = null

    @SerializedName("skip")
    val skip: Int? = null

    @SerializedName("limit")
    val limit: Int? = null
}