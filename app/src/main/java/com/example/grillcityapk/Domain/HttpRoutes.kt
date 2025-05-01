package com.example.grillcityapk.Domain

object HttpRoutes {
    private const val BASE_URL = "http://10.0.2.2:5178" // Для эмулятора Android
    const val PRODUCTS = "$BASE_URL/products"
    const val ORDERS = "$BASE_URL/orders"
    const val CREATE_ORDER = "$BASE_URL/CreateOrder"
    const val SIGN_IN = "$BASE_URL/login"
    const val SIGN_UP = "$BASE_URL/register"
    const val PROVIDERS = "$BASE_URL/providers"
    const val PRODUCT_TYPES = "$BASE_URL/producttypes"
    const val ORDER_HISTORY = "$BASE_URL/orders/history"
    const val CREATE_MOBILE_ORDER = "$BASE_URL/CreateMobileOrder"
    const val ORDERS_BY_USER = "$BASE_URL/ordersByUser"
}