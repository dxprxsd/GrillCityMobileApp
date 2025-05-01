package com.example.grillcityapk.Models

data class OrderProductResponse(
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val pricePerItem: Double,
    val total: Double
)