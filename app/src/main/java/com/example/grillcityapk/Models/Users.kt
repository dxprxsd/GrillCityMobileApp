package com.example.grillcityapk.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("users")
data class Users(
    @SerialName("userID") val userID: Int,
    @SerialName("sname") val sname: String?,
    @SerialName("fname") val fname: String?,
    @SerialName("patronumic") val patronumic: String?,
    @SerialName("phonenumber") val phonenumber: String?,
    @SerialName("userPassword") val userPassword: String?,
    @SerialName("userLogin") val userLogin: String?
)