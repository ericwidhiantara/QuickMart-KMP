package com.luckyfrog.quickmart.features.product.data.models.response

import com.google.gson.annotations.SerializedName

data class ProductResponseDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("discountPercentage")
    val discountPercentage: Double,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("stock")
    val stock: Long,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("sku")
    val sku: String,
    @SerializedName("weight")
    val weight: Long,
    @SerializedName("dimensions")
    val dimensions: DimensionsResponseDto,
    @SerializedName("warrantyInformation")
    val warrantyInformation: String,
    @SerializedName("shippingInformation")
    val shippingInformation: String,
    @SerializedName("availabilityStatus")
    val availabilityStatus: String,
    @SerializedName("reviews")
    val reviews: List<ReviewResponseDto>,
    @SerializedName("returnPolicy")
    val returnPolicy: String,
    @SerializedName("minimumOrderQuantity")
    val minimumOrderQuantity: Long,
    @SerializedName("meta")
    val meta: MetaResponseDto,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("brand")
    val brand: String? = null
)

data class DimensionsResponseDto(
    @SerializedName("width")
    val width: Double,
    @SerializedName("height")
    val height: Double,
    @SerializedName("depth")
    val depth: Double
)

data class MetaResponseDto(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("barcode")
    val barcode: String,
    @SerializedName("qrCode")
    val qrCode: String
)

data class ReviewResponseDto(
    @SerializedName("rating")
    val rating: Long,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("reviewerName")
    val reviewerName: String,
    @SerializedName("reviewerEmail")
    val reviewerEmail: String
)