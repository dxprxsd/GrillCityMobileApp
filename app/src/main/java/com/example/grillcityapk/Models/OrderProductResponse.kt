package com.example.grillcityapk.Models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class OrderProductResponse(
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val pricePerItem: Double,
    val total: Double
)