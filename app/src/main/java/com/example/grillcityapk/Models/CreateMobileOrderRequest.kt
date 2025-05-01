package com.example.grillcityapk.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateMobileOrderRequest(
    @SerialName("clientId")
    val clientId: Int,
    @SerialName("products")
    val products: Map<Int, Int>, // ProductId to Quantity
    @SerialName("discountId")
    val discountId: Int? = null
)