package com.example.grillcityapk.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Products(
    @SerialName("Id") val Id: Int,
    @SerialName("ProductName") val ProductName: String,
    @SerialName("ProductTypeId") val ProductTypeId: Int?,
    @SerialName("ProviderId") val ProviderId: Int?,
    @SerialName("Photo") val Photo: String? = null,
    @SerialName("QuantityInStock") val QuantityInStock: Int,
    @SerialName("Price") val Price: Float
)
