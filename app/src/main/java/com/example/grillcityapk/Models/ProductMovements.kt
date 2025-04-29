package com.example.grillcityapk.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("product_movements")
data class ProductMovements(
    @SerialName("id") val id: Int,
    @SerialName("product_id") val product_id: Int,
    @SerialName("movement_type") val movement_type: String,
    @SerialName("quantity") val quantity: Int,
    @SerialName("movement_date") val movement_date: String
)