package com.example.grillcityapk.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.grillcityapk.MainViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.example.grillcityapk.Models.Products
import com.example.grillcityapk.R

@Composable
fun CartScreen(navController: NavHostController, viewModel: MainViewModel) {
    val cartItems by viewModel.cartItems.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        // Шапка с фоном и логотипом
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFC21631))
        ) {
            // Логотип
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

            // Надпись "Корзина"
            Text(
                text = "Корзина",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )
        }

        // Основной контент с отступом для нижней панели
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 56.dp)
        ) {
            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Корзина пуста")
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(cartItems) { product ->
                            CartItemCard(
                                product = product,
                                onRemove = { viewModel.removeFromCart(it) }
                            )
                        }
                    }

                    // Блок с общей суммой
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Итого: ${viewModel.getCartTotal()} ₽",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Button(
                            onClick = { /* Оформление заказа */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFC21631)
                            )
                        ) {
                            Text("Оформить заказ")
                        }
                    }
                }
            }
        }

        // Панель навигации внизу экрана
        BottomNavigationBar(navController)
    }
}

@Composable
fun CartItemCard(
    product: Products,  // Обязательный параметр
    onRemove: (Products) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Изображение товара
            AsyncImage(
                model = product.Photo ?: "",
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Информация о товаре
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.ProductName ?: "Без названия",
                    fontWeight = FontWeight.Bold
                )
                Text("${product.Price} ₽")
            }

            // Кнопка удаления
            IconButton(onClick = { onRemove(product) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Удалить из корзины",
                    tint = Color(0xFFC21631)
                )
            }
        }
    }
}