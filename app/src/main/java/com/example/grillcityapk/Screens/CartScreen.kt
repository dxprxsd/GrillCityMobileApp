package com.example.grillcityapk.Screens

import android.widget.Toast
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.example.grillcityapk.Models.Products
import com.example.grillcityapk.R

@Composable
fun CartScreen(navController: NavHostController, viewModel: MainViewModel) {
    val cartItems by viewModel.cartItems.collectAsState()
    val context = LocalContext.current
    val clientId = viewModel.currentClientId

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
                                onRemove = { viewModel.removeFromCart(it) },
                                onIncrease = { viewModel.increaseQuantity(it) },
                                onDecrease = { viewModel.decreaseQuantity(it) }
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
                            onClick = {
                                if (clientId != null) {
                                    viewModel.createMobileOrder(
                                        clientId = clientId,
                                        discountId = null
                                    ) { success, message ->
                                        if (success) {
                                            Toast.makeText(context, "Заказ оформлен!", Toast.LENGTH_LONG).show()
                                        } else {
                                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                        }
                                    }
                                } else {
                                    Toast.makeText(context, "Для оформления войдите в систему", Toast.LENGTH_LONG).show()
                                    navController.navigate("login")
                                }
                            },
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
    product: Products,
    onRemove: (Products) -> Unit,
    onIncrease: (Products) -> Unit,
    onDecrease: (Products) -> Unit
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
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .border(1.dp, Color(0xFFC21631), RoundedCornerShape(8.dp))
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                val context = LocalContext.current
                val imageRes = remember(product.Photo) {
                    try {
                        product.Photo?.substringBeforeLast(".")?.let { name ->
                            context.resources.getIdentifier(name, "drawable", context.packageName)
                        } ?: R.drawable.imagezaglushka
                    } catch (e: Exception) {
                        R.drawable.imagezaglushka
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

            Spacer(modifier = Modifier.width(16.dp))

            // Информация о товаре и кнопки количества
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.ProductName ?: "Без названия",
                    fontWeight = FontWeight.Bold
                )
                Text("${product.Price} ₽")

                // Строка с кнопками количества
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    // Кнопка уменьшения количества
                    IconButton(
                        onClick = { onDecrease(product) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.minus),
                            contentDescription = "Уменьшить количество",
                            modifier = Modifier.size(18.dp),
                            colorFilter = ColorFilter.tint(Color(0xFFC21631))
                        )
                    }

                    // Отображение текущего количества
                    Text(
                        text = "${product.QuantityInCart}",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontSize = 16.sp
                    )

                    // Кнопка увеличения количества
                    IconButton(
                        onClick = { onIncrease(product) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.plus),
                            contentDescription = "Увеличить количество",
                            modifier = Modifier.size(18.dp),
                            colorFilter = ColorFilter.tint(Color(0xFFC21631))
                        )
                    }
                }
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