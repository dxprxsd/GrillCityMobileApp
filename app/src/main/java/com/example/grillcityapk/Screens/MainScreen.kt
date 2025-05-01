package com.example.grillcityapk.Screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.grillcityapk.MainViewModel
import com.example.grillcityapk.Models.Products
import com.example.grillcityapk.R
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController


@SuppressLint("UnrememberedMutableState")
@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {
    // Observe the products, filtered haircuts, and types
    val haircuts by viewModel.haircuts
    val searchQuery = viewModel.searchQuery
    val filteredHaircuts = viewModel.filteredHaircuts
    val haircutsTypes by viewModel.haircutstypes.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Все типы") }
    var selectedType by remember { mutableStateOf<Int?>(null) }

    // Fetch products and types
    LaunchedEffect(Unit) {
        viewModel.fetchHaircuts()  // Fetch products
        viewModel.getTypeHaircut()  // Fetch product types
    }

    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFFA7262F), Color.White),
        start = Offset(0f, 0f),
        end = Offset.Infinite
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(WindowInsets.statusBars.asPaddingValues()), // <-- отступ от системной панели
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Шапка с логотипом
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.minilogogc),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(70.dp)
                    .width(160.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Основной блок с серым фоном и скруглением
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(Color(0xFFE8E8E8), shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Строка поиска и фильтра — переместили сюда
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFC21631), RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .widthIn(max = 180.dp), // Установка максимальной ширины
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = viewModel.searchQuery.value,
                        onValueChange = {
                            viewModel.searchQuery.value = it
                            viewModel.filteredHaircutMethod() // Передаём текущий selectedType
                        },
                        placeholder = { Text("Поиск") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedPlaceholderColor = Color.White.copy(alpha = 0.7f),
                            focusedPlaceholderColor = Color.White.copy(alpha = 0.7f)
                        )
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(
                        onClick = { expanded = !expanded },
                        modifier = Modifier
                            .width(150.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8E8E8)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = selectedItem.ifBlank { "Фильтр" }, color = Color.Black)
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.width(200.dp)
                    ) {
                        // Добавляем пункт "Все товары" в выпадающий список
                        DropdownMenuItem(
                            text = { Text("Все типы") },
                            onClick = {
                                selectedItem = "Все типы"
                                viewModel.setSelectedType(null)
                                expanded = false
                            }
                        )

                        haircutsTypes.forEach { type ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = type.typeName.toString(), fontSize = 15.sp)
                                },
                                onClick = {
                                    selectedItem = type.typeName.toString()
                                    viewModel.setSelectedType(type.id)
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Список карточек
                if (filteredHaircuts.value.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(48.dp),
                            color = Color(0xFFC21631)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(filteredHaircuts.value) { product ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = BorderStroke(2.dp, Color(0xFFC21631))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Фото слева
                                    Box(
                                        modifier = Modifier
                                            .size(100.dp)
                                            .border(2.dp, Color(0xFFC21631), RoundedCornerShape(8.dp))
                                            .padding(4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        val context = LocalContext.current
                                        val imageRes = remember(product.Photo) {
                                            try {
                                                product.Photo?.substringBeforeLast(".")?.let { name ->
                                                    context.resources.getIdentifier(name, "drawable", context.packageName)
                                                } ?: R.drawable.imagezaglushka // Используем заглушку по умолчанию
                                            } catch (e: Exception) {
                                                R.drawable.imagezaglushka // Используем заглушку при ошибке
                                            }
                                        }

                                        Image(
                                            painter = painterResource(id = imageRes),
                                            contentDescription = product.ProductName,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clip(RoundedCornerShape(8.dp)),
                                            contentScale = ContentScale.Crop
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    // Информация справа
                                    Column(
                                        verticalArrangement = Arrangement.SpaceEvenly,
                                        modifier = Modifier.fillMaxHeight()
                                    ) {
                                        Text(
                                            text = product.ProductName ?: "Без названия",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )

                                        Text(
                                            text = "Кол-во: ${product.QuantityInStock ?: 0}",
                                            fontSize = 14.sp
                                        )

                                        Text(
                                            text = "${product.Price ?: 0.0} ₽",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFC21631)
                                        )
                                        // Кнопка добавления в корзину
                                        IconButton(
                                            onClick = {
                                                viewModel.addToCart(product)
                                                println("Текущее состояние корзины: ${viewModel.cartItems.value}")
                                                println("Добавленный товар: ${product.ProductName}") // Логирование
                                            },
                                            modifier = Modifier.size(48.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Add,
                                                contentDescription = "Add to cart",
                                                tint = Color(0xFFC21631),
                                                modifier = Modifier.size(30.dp)
                                            )
                                        }

                                        // Добавляем Spacer, чтобы контент не перекрывался нижней панелью
                                        Spacer(modifier = Modifier.weight(1f))

                                        // Нижняя панель навигации
                                        BottomNavigationBar(navController)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        BottomNavigationBar(navController)
    }
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFFC21631)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Кнопка главной страницы
        IconButton(
            onClick = { navController.navigate("main_screen") },
            modifier = Modifier.weight(1f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Главная",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Text("Главная", color = Color.White, fontSize = 12.sp)
            }
        }

        // Кнопка корзины
        IconButton(
            onClick = { navController.navigate("cart_screen") },
            modifier = Modifier.weight(1f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Корзина",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Text("Корзина", color = Color.White, fontSize = 12.sp)
            }
        }

        // Кнопка профиля пользователя
        IconButton(
            onClick = { navController.navigate("user_screen") },
            modifier = Modifier.weight(1f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Профиль",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Text("Профиль", color = Color.White, fontSize = 12.sp)
            }
        }
    }
}