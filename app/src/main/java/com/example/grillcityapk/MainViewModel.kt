package com.example.grillcityapk

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grillcityapk.Domain.RetrofitClient
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

//    private val _rmhaircuts = mutableStateOf(listOf<Products>())
//    val haircuts: State<List<Products>> = _rmhaircuts
//
//
//    var searchQuery = mutableStateOf("")
//
//    private val _filteredHaircuts = mutableStateOf(listOf<Products>())
//
//    val filteredHaircuts: State<List<Products>> = _filteredHaircuts
//
//    //private val _rmclothes = mutableStateOf(listOf<Haircut>())
//    //val clothes: State<List<Haircut>> = _rmclothes
//
//    //для фильтрации по выбранным пунктам
//    var typeOfReadyClothes by mutableStateOf<Int?>(null)
//
//    // Метод для фильтрации по выбранным критериям
//    fun filteredHaircutMethod() {
//        _filteredHaircuts.value = _rmhaircuts.value
//        //для фильтрации по выбранным пунктам
//        if(typeOfReadyClothes != null){
//            _filteredHaircuts.value = _filteredHaircuts.value.filter { Products -> Products.product_type_id == typeOfReadyClothes }
//        }
//        // Фильтрация по поисковому запросу
//        if(searchQuery.value.isNotEmpty()){
//            _filteredHaircuts.value = _filteredHaircuts.value.filter { Products ->
//                Products.product_name.lowercase(Locale.getDefault())
//                    .contains(searchQuery.value.lowercase(Locale.getDefault()))
//            }
//        }
//    }
//
//    fun fetchHaircuts() {
//        // Запрос к базе данных (например, Supabase)
//        viewModelScope.launch {
//            try {
//                val fetchedClothes = Constants.supabase.from("products")
//                    .select()
//                    .decodeList<Products>()
//
//                _rmhaircuts.value = fetchedClothes
//                filteredHaircutMethod() //применить фильтрацию
//            } catch (e: Exception) {
//                Log.e("MainViewModel", "Ошибка загрузки причесок: ${e.message}")
//            }
//        }
//    }
//
//    private val _hairtypes: MutableStateFlow<List<Products>> = MutableStateFlow(listOf())
//    val haircutstypes: StateFlow<List<Products>> = _hairtypes.asStateFlow()
//
//    //private val _emploeetypes: MutableStateFlow<List<Employee>> = MutableStateFlow(listOf())
//    //val emploeesstypes: StateFlow<List<Employee>> = _emploeetypes.asStateFlow()
//
//    fun getTypeHaircut() {
//        // Запрос к базе данных (например, Supabase)
//        viewModelScope.launch {
//            try {
//                val fetchedTypeHaircuts = Constants.supabase.from("products")
//                    .select()
//                    .decodeList<Products>()
//
//                _hairtypes.value = fetchedTypeHaircuts
//                //filteredClothess() //применить фильтрацию
//            } catch (e: Exception) {
//                Log.e("MainViewModel", "Ошибка загрузки типов причесок: ${e.message}")
//            }
//        }
//    }
}