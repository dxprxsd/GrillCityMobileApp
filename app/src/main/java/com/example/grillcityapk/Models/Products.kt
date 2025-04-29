package com.example.grillcityapk.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("products")
data class Products(
    @SerialName("id") val id: Int,
    @SerialName("product_name") val product_name: String,
    @SerialName("product_type_id") val product_type_id: Int?,
    @SerialName("provider_id") val provider_id: Int?,
    @SerialName("photo") val photo: String? = null,
    @SerialName("quantity_in_stock") val quantity_in_stock: Int,
    @SerialName("price") val price: Float
)
