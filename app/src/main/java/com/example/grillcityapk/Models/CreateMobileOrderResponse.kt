package com.example.grillcityapk.Models

data class CreateMobileOrderResponse(
    val message: String,
    val orderId: Int,
    val client: String,
    val code: String,
    val products: List<OrderProductResponse>,
    val totalPrice: Double,
    val orderDate: String
)