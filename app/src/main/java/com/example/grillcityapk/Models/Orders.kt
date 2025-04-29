package com.example.grillcityapk.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("orders")
data class Orders(
    @SerialName("id") val id: Int,
    @SerialName("product_id") val product_id: Int,
    @SerialName("discount_id") val discount_id: Int,
    @SerialName("date_of_order") val date_of_order: String, // ISO 8601 format
    @SerialName("final_price") val final_price: Float
)