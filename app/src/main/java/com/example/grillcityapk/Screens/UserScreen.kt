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
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.grillcityapk.MainViewModel
import com.example.grillcityapk.R
import java.text.SimpleDateFormat
import androidx.compose.runtime.getValue
import com.example.grillcityapk.Models.OrderResponse
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun UserScreen(navController: NavHostController, viewModel: MainViewModel = viewModel()) {
    val user = viewModel.currentUser.collectAsState().value
    val orders by viewModel.userOrders.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val clientId = viewModel.currentClientId

    LaunchedEffect(clientId) {
        clientId?.let {
            viewModel.fetchUserOrders(it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFA7262F))
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        // Верхний блок с логотипом и заголовком
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color(0xFFA7262F))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Мои заказы",
                fontSize = 33.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Image(
                painter = painterResource(id = R.drawable.minilogogc),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
            )
        }

        // Main content
        Box(
            modifier = Modifier
                .weight(1f)
                .background(Color(0xFFE8E8E8), shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFFC21631)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "${user?.sname ?: ""} ${user?.fname ?: ""} ${user?.patronumic ?: ""}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Orders list - CORRECTED VERSION
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(items = orders, key = { it.orderId }) { order ->
                            OrderCard(order = order)
                        }
                    }
                }
            }
        }

        // Bottom navigation bar
        BottomNavigationBar(navController)
    }
}

@Composable
fun OrderCard(order: OrderResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Заказ #${order.orderId}",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = order.date,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Статус: ${order.status}",
                color = when (order.status) {
                    "Выполнен" -> Color.Green
                    "Отменен" -> Color.Red
                    else -> Color(0xFFC21631)
                }
            )

            Text(
                text = "Код получения: ${order.code}",
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Товары:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Column {
                order.products.forEach { product ->
                    Text(
                        text = product.productName,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }
    }
}
