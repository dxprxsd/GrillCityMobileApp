package com.example.grillcityapk.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Products(
    @SerialName("Id") val id: Int,
    @SerialName("ProductName") val product_name: String,
    @SerialName("ProductTypeId") val product_type_id: Int?,
    @SerialName("ProviderId") val provider_id: Int?,
    @SerialName("Photo") val photo: String? = null,
    @SerialName("QuantityInStock") val quantity_in_stock: Int,
    @SerialName("Price") val price: Float
)
