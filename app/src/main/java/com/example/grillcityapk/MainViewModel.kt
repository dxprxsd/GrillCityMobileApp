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

class MainViewModel : ViewModel() {
    //переменная для результата входа и регистрации
    var authResult = mutableStateOf("")
    private val _authResult = MutableLiveData<Boolean>()

    //uid текущего пользователя
    var currentUserUid = mutableStateOf<String?>(null)

    var profileCreated = mutableStateOf(false)

//    private val _products = mutableStateListOf<Products>()
//    val products: List<Products> get() = _products

//    private val _isLoading = mutableStateOf(false)
//    val isLoading: State<Boolean> get() = _isLoading

//    //Функция для входа в приложении
//    fun onSignInEmailPassword(emailUser: String, passwordUser: String) {
//        viewModelScope.launch {
//            try {
//                // Вход пользователя
//                val user = Constants.supabase.auth.signInWith(Email) {
//                    email = emailUser
//                    password = passwordUser
//                }
//
//                // Сохранение ID текущего пользователя
//                currentUserUid.value = Constants.supabase.auth.currentUserOrNull()?.id
//
//                println("Current user uid: $currentUserUid")
//
//                println("Success")
//                authResult.value = "Success" // Устанавливаем успешный результат
//            } catch (e: Exception) {
//                println("Error")
//                authResult.value = "Error" // Устанавливаем ошибочный результат
//                println(e.message.toString())
//            }
//        }
//    }

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

//    fun loadProducts(typeId: Int? = null) {
//        viewModelScope.launch {
//            _isLoading.value = true
//            try {
//                val response = RetrofitClient.apiService.getProducts(typeId)
//                _products.clear()
//                _products.addAll(response)
//            } catch (e: Exception) {
//                Log.e("MainViewModel", "Error loading products", e)
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }


//    //Функция для регистрации в приложении
//    fun onSignUpEmailPassword(emailUser: String, passwordUser: String) {
//        viewModelScope.launch {
//            try {
//                // Регистрация пользователя
//                val user = Constants.supabase.auth.signUpWith(Email) {
//                    email = emailUser
//                    password = passwordUser
//                }
//
//                // Сохранение ID текущего пользователя после успешной регистрации
//                currentUserUid.value = Constants.supabase.auth.currentUserOrNull()?.id
//
//                println("Current user uid: $currentUserUid")
//
//                println("Registration Success")
//                authResult.value = "Registration Success"
//            } catch (e: Exception) {
//                println("Registration Error")
//                authResult.value = "Registration Error"
//                println(e.message.toString())
//            }
//        }
//    }

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

    // Подключение ApiService
    private val apiService = RetrofitClient.apiService

    // Метод фильтрации
    fun filteredHaircutMethod() {
        _filteredHaircuts.value = _rmhaircuts.value

        if (searchQuery.value.isNotEmpty()) {
            _filteredHaircuts.value = _filteredHaircuts.value.filter {
                it.product_name.lowercase(Locale.getDefault())
                    .contains(searchQuery.value.lowercase(Locale.getDefault()))
            }
        }
    }

    // Получение товаров с фильтрацией по типу
    fun fetchHaircuts() {
        viewModelScope.launch {
            try {
                val fetchedProducts = apiService.getProducts(typeOfReadyClothes)
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
}