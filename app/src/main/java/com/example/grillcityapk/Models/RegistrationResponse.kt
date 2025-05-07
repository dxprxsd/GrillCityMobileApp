package com.example.grillcityapk.Models

import kotlinx.serialization.SerialName

data class RegistrationResponse(
    @SerialName("message") val message: String,
    @SerialName("userId") val userId: Int,
    @SerialName("fullName") val fullName: String,
    @SerialName("phoneNumber") val phoneNumber: String
)