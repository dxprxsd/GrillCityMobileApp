package com.example.grillcityapk

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grillcityapk.Domain.RetrofitClient
import com.example.grillcityapk.Models.Product_type
import com.example.grillcityapk.Models.Products
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import androidx.compose.foundation.lazy.items
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {
    //переменная для результата входа и регистрации
    var authResult = mutableStateOf("")
    private val _authResult = MutableLiveData<Boolean>()

    // Подключение ApiService
    private val apiService = RetrofitClient.apiService

    //uid текущего пользователя
    var currentUserUid = mutableStateOf<String?>(null)

    var profileCreated = mutableStateOf(false)

    fun onSignInEmailPassword(emailUser: String, passwordUser: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.login(
                    login = emailUser,
                    password = passwordUser
                )

                // Проверяем наличие userId как индикатора успеха
                if (response.userId != null) {
                    println("Login successful! UserId: ${response.userId}")
                    currentUserUid.value = response.userId.toString()
                    authResult.value = "Success"
                } else {
                    println("Login failed: ${response.message ?: "No message"}")
                    authResult.value = "Error"
                }
            } catch (e: Exception) {
                println("Exception during login: ${e.localizedMessage}")
                authResult.value = "Error"
            }
        }
    }

    private val _products = mutableStateOf<List<Products>>(emptyList())
    val products: State<List<Products>> = _products

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getProducts()
                Log.d("PRODUCTS", response.toString())
                _products.value = response
            } catch (e: Exception) {
                Log.e("PRODUCTS", "Error loading products", e)
            }
        }
    }


    private val _rmhaircuts = mutableStateOf(listOf<Products>())
    val haircuts: State<List<Products>> = _rmhaircuts

    var searchQuery = mutableStateOf("")
    var typeOfReadyClothes by mutableStateOf<Int?>(null)

    private val _filteredHaircuts = mutableStateOf(listOf<Products>())
    val filteredHaircuts: State<List<Products>> = _filteredHaircuts

    private val _hairtypes = MutableStateFlow<List<Product_type>>(emptyList())
    val haircutstypes: StateFlow<List<Product_type>> = _hairtypes.asStateFlow()

    private val _selectedType = mutableStateOf<Int?>(null)
    val selectedType: State<Int?> get() = _selectedType

    // Метод фильтрации (исправленный)
    internal fun filteredHaircutMethod() {
        // Фильтруем сначала по поиску, затем по типу
        var result = _rmhaircuts.value

        // Применяем поисковый фильтр
        if (searchQuery.value.isNotEmpty()) {
            result = result.filter {
                it.ProductName.contains(searchQuery.value, ignoreCase = true)
            }
        }

        // Применяем фильтр по типу (если typeOfReadyClothes установлен)
        typeOfReadyClothes?.let { typeId ->
            result = result.filter { it.ProductTypeId == typeId }
        }

        _filteredHaircuts.value = result
    }


    // Установка типа для фильтрации
    fun setSelectedType(typeId: Int?) {
        typeOfReadyClothes = typeId
        filteredHaircutMethod()
    }

    // Получение товаров с фильтрацией по типу
    fun fetchHaircuts() {
        viewModelScope.launch {
            try {
                val fetchedProducts = apiService.getProducts()
                _rmhaircuts.value = fetchedProducts
                filteredHaircutMethod()
            } catch (e: Exception) {
                Log.e("MainViewModel", "Ошибка загрузки товаров: ${e.message}")
            }
        }
    }

    // Получение типов товаров
    fun getTypeHaircut() {
        viewModelScope.launch {
            try {
                val fetchedTypes = apiService.getProductTypes()
                _hairtypes.value = fetchedTypes
            } catch (e: Exception) {
                Log.e("MainViewModel", "Ошибка загрузки типов товаров: ${e.message}")
            }
        }
    }

    // Корзина
    private val _cartItems = MutableStateFlow<List<Products>>(emptyList())
    val cartItems: StateFlow<List<Products>> = _cartItems.asStateFlow()

    // Для добавления товара
    fun addToCart(product: Products) {
        _cartItems.update { current ->
            if (current.any { it.Id == product.Id }) current
            else current + product
        }
    }

    // Для удаления товара
    fun removeFromCart(product: Products) {
        _cartItems.update { currentItems ->
            currentItems.filterNot { it.Id == product.Id }
        }
    }

    // Для подсчета суммы
    fun getCartTotal(): Float {
        return _cartItems.value.sumOf { it.Price.toDouble() }.toFloat()
    }
}