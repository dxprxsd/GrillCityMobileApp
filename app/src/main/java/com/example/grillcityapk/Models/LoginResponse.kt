package com.example.grillcityapk.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("userId") val userId: Int? = null,
    @SerialName("message") val message: String,
    @SerialName("fullName") val fullName: String? = null,
    @SerialName("phoneNumber") val phoneNumber: String? = null
)