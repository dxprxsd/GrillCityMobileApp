package com.example.grillcityapk

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grillcityapk.Domain.RetrofitClient
import com.example.grillcityapk.Models.Product_type
import com.example.grillcityapk.Models.Products
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.grillcityapk.Models.CreateMobileOrderRequest
import com.example.grillcityapk.Models.OrderResponse
import com.example.grillcityapk.Models.Users
import kotlinx.coroutines.flow.update

class MainViewModel() : ViewModel() {
    //переменная для результата входа и регистрации
    var authResult = mutableStateOf("")
    private val _authResult = MutableLiveData<Boolean>()

    var currentClientId by mutableStateOf<Int?>(null)
        private set


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
                    currentClientId = response.userId  // сохранить ID клиента
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

//    // Для добавления товара
//    fun addToCart(product: Products) {
//        _cartItems.update { current ->
//            if (current.any { it.Id == product.Id }) current
//            else current + product
//        }
//    }

    fun addToCart(product: Products) {
        _cartItems.update { current ->
            val existing = current.find { it.Id == product.Id }
            if (existing != null) {
                current.map {
                    if (it.Id == product.Id)
                        it.copy(QuantityInCart = it.QuantityInCart + 1)
                    else it
                }
            } else {
                current + product.copy(QuantityInCart = 1)
            }
        }
    }

    // Увеличить количество товара
    fun increaseQuantity(product: Products) {
        _cartItems.update { current ->
            current.map {
                if (it.Id == product.Id) {
                    it.copy(QuantityInCart = it.QuantityInCart + 1)
                } else {
                    it
                }
            }
        }
    }

    // Уменьшить количество товара (удалить если количество <= 1)
    fun decreaseQuantity(product: Products) {
        _cartItems.update { current ->
            current.flatMap {
                if (it.Id == product.Id) {
                    if (it.QuantityInCart > 1) {
                        listOf(it.copy(QuantityInCart = it.QuantityInCart - 1))
                    } else {
                        emptyList() // удаляем если количество стало 0
                    }
                } else {
                    listOf(it)
                }
            }
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
        return _cartItems.value.sumOf { it.Price.toDouble() * it.QuantityInCart }.toFloat()
    }


    fun createMobileOrder(clientId: Int, discountId: Int?, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                // Преобразуем корзину в формат для API
                val productMap = _cartItems.value.associate { it.Id to it.QuantityInCart }

                if (productMap.isEmpty()) {
                    onResult(false, "Корзина пуста")
                    return@launch
                }

                // Создаем запрос
                val request = CreateMobileOrderRequest(
                    clientId = clientId,
                    products = productMap, // Уже содержит Int ключи
                    discountId = discountId
                )

                val response = apiService.createMobileOrder(request)

                // Очищаем корзину после успешного заказа
                _cartItems.value = emptyList()

                val resultMsg = buildString {
                    appendLine("Заказ №${response.orderId} успешно оформлен!")
                    appendLine("Код: ${response.code}")
                    appendLine("Сумма: ${response.totalPrice} ₽")
                    appendLine("Дата: ${response.orderDate}")
                }

                onResult(true, resultMsg)
            } catch (e: Exception) {
                onResult(false, "Ошибка при оформлении заказа: ${e.localizedMessage}")
            }
        }
    }

    private val _currentUser = MutableStateFlow<Users?>(null)
    val currentUser: StateFlow<Users?> = _currentUser.asStateFlow()

    private val _userOrders = MutableStateFlow<List<OrderResponse>>(emptyList())
    val userOrders: StateFlow<List<OrderResponse>> = _userOrders.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun fetchUserOrders(userId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getUserOrders(userId)
                _userOrders.value = response
            } catch (e: Exception) {
                Log.e("UserScreen", "Error fetching orders", e)
            } finally {
                _isLoading.value = false
            }
        }
    }





}