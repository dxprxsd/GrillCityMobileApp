package com.example.grillcityapk.Models

import kotlinx.serialization.Serializable

@Serializable
data class OrderResponse(
    val orderId: Int,
    val date: String,
    val status: String,
    val code: String,
    val products: List<OrderProductResponse>
)