package com.example.grillcityapk.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("providers")
data class Providers(
    @SerialName("id") val id: Int,
    @SerialName("provider_name") val provider_name: String?
)