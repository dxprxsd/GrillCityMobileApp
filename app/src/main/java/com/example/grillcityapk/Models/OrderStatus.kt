package com.example.grillcityapk.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("order_status")
data class OrderStatus(
    @SerialName("orderStatusID") val orderStatusID: Int,
    @SerialName("statusName") val statusName: String?
)