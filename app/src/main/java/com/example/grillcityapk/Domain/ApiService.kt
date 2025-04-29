package com.example.grillcityapk.Domain

import com.example.grillcityapk.Models.CreateOrderResponse
import com.example.grillcityapk.Models.LoginResponse
import com.example.grillcityapk.Models.Orders
import com.example.grillcityapk.Models.Product_type
import com.example.grillcityapk.Models.Products
import retrofit2.http.*

interface ApiService {

    @GET("products")
    suspend fun getProducts(@Query("typeId") typeId: Int? = null): List<Products>

    @GET("productTypes")
    suspend fun getProductTypes(): List<Product_type>

    @POST("CreateOrder")
    suspend fun createOrder(
        @Query("productId") productId: Int,
        @Query("discountId") discountId: Int?,
        @Query("quantity") quantity: Int
    ): CreateOrderResponse

    @POST("login")
    suspend fun login(
        @Query("login") login: String,
        @Query("password") password: String
    ): LoginResponse

    @GET("orders")
    suspend fun getOrders(): List<Orders>
}