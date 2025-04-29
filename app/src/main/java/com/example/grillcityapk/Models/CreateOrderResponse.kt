package com.example.grillcityapk.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("create_order_response")
data class CreateOrderResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("message") val message: String,
    @SerialName("orderId") val orderId: Int? = null
)