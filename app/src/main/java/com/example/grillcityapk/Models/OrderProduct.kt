package com.example.grillcityapk.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("order_product")
data class OrderProduct(
    @SerialName("orderID") val orderID: Int,
    @SerialName("productsId") val productsId: Int,
    @SerialName("countInOrder") val countInOrder: Int
)
