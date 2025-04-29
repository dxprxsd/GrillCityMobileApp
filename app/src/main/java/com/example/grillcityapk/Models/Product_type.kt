package com.example.grillcityapk.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("product_type")
data class Product_type(
    @SerialName("id") val id: Int,
    @SerialName("typeName") val typeName: String?
)
