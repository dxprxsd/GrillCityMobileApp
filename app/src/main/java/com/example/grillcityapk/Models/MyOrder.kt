package com.example.grillcityapk.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("my_order")
data class MyOrder(
    @SerialName("orderID") val orderID: Int,
    @SerialName("dateOfOrder") val dateOfOrder: String,
    @SerialName("clientID") val clientID: Int?,
    @SerialName("codeForTakeProduct") val codeForTakeProduct: String,
    @SerialName("orderStatus") val orderStatus: Int
)