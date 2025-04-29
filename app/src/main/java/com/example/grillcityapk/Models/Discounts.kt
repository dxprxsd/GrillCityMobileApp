package com.example.grillcityapk.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("discounts")
data class Discounts(
    @SerialName("id") val id: Int,
    @SerialName("discount_percent") val discount_percent: Float
)