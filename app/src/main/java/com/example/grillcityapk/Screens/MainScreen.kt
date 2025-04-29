package com.example.grillcityapk.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.grillcityapk.MainViewModel
import com.example.grillcityapk.R

@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {
//    val haircuts by viewModel.haircuts
//    val searchQuery = viewModel.searchQuery
//    val filteredHaircutss by viewModel.filteredHaircuts

    var expanded by remember { mutableStateOf(false) }
    val list = listOf("Все прически", "Мужские", "Женские")
    var selectedItem by remember { mutableStateOf("") }

//    LaunchedEffect(Unit) {
//        viewModel.fetchHaircuts()
//    }

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

//        // Основной блок с серым фоном и скруглением
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(0xFFE8E8E8), shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(horizontal = 16.dp, vertical = 12.dp)
//            ) {
//                // Строка поиска и фильтра — переместили сюда
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(Color(0xFFC21631), RoundedCornerShape(12.dp))
//                        .padding(horizontal = 16.dp, vertical = 8.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    TextField(
//                        value = viewModel.searchQuery.value,
//                        onValueChange = {
//                            viewModel.searchQuery.value = it
//                            viewModel.filteredHaircutMethod()
//                        },
//                        placeholder = { Text("Поиск") },
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(start = 8.dp),
//                        singleLine = true,
//                        shape = RoundedCornerShape(8.dp),
//                        colors = TextFieldDefaults.colors(
//                            unfocusedContainerColor = Color.Transparent,
//                            focusedContainerColor = Color.Transparent,
//                            unfocusedIndicatorColor = Color.White,
//                            focusedIndicatorColor = Color.White,
//                            unfocusedTextColor = Color.White,
//                            focusedTextColor = Color.White,
//                            unfocusedPlaceholderColor = Color.White.copy(alpha = 0.7f),
//                            focusedPlaceholderColor = Color.White.copy(alpha = 0.7f)
//                        )
//                    )
//
//                    Spacer(modifier = Modifier.width(10.dp))
//
//                    Button(
//                        onClick = { expanded = !expanded },
//                        modifier = Modifier
//                            .width(120.dp)
//                            .height(50.dp),
//                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8E8E8)),
//                        shape = RoundedCornerShape(12.dp)
//                    ) {
//                        Text(text = selectedItem.ifBlank { "Фильтр" }, color = Color.Black)
//                    }
//
//                    DropdownMenu(
//                        expanded = expanded,
//                        onDismissRequest = { expanded = false },
//                        modifier = Modifier.width(200.dp)
//                    ) {
//                        list.forEach { label ->
//                            DropdownMenuItem(
//                                text = {
//                                    Text(text = label, fontSize = 15.sp)
//                                },
//                                onClick = {
//                                    selectedItem = label
//                                    viewModel.typeOfReadyClothes = when (label) {
//                                        "Мужские" -> 1
//                                        "Женские" -> 2
//                                        else -> null
//                                    }
//                                    viewModel.filteredHaircutMethod()
//                                    expanded = false
//                                }
//                            )
//                        }
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Список карточек
//                if (haircuts.isEmpty()) {
//                    Box(modifier = Modifier.fillMaxSize()) {
//                        CircularProgressIndicator(
//                            modifier = Modifier
//                                .align(Alignment.Center)
//                                .size(48.dp),
//                            color = Color(0xFFC21631)
//                        )
//                    }
//                } else {
//                    LazyColumn(
//                        modifier = Modifier.fillMaxSize(),
//                        verticalArrangement = Arrangement.spacedBy(16.dp)
//                    ) {
//                        items(filteredHaircutss, key = { it.id }) { product ->
//                            Card(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .height(120.dp),
//                                shape = RoundedCornerShape(12.dp),
//                                colors = CardDefaults.cardColors(containerColor = Color.White),
//                                border = BorderStroke(2.dp, Color(0xFFC21631))
//                            ) {
//                                Row(
//                                    modifier = Modifier
//                                        .fillMaxSize()
//                                        .padding(8.dp),
//                                    verticalAlignment = Alignment.CenterVertically
//                                ) {
//                                    // Фото слева
//                                    Box(
//                                        modifier = Modifier
//                                            .size(100.dp)
//                                            .border(2.dp, Color(0xFFC21631), RoundedCornerShape(8.dp))
//                                            .padding(4.dp),
//                                        contentAlignment = Alignment.Center
//                                    ) {
//                                        val photoUrl = rememberAsyncImagePainter(
//                                            model = ImageRequest.Builder(LocalContext.current)
//                                                .data(product.photo)
//                                                .size(100, 100)
//                                                .build()
//                                        ).state
//
//                                        when (photoUrl) {
//                                            is AsyncImagePainter.State.Success -> {
//                                                Image(
//                                                    painter = photoUrl.painter!!,
//                                                    contentDescription = null,
//                                                    modifier = Modifier
//                                                        .fillMaxSize()
//                                                        .clip(RoundedCornerShape(8.dp)),
//                                                    contentScale = ContentScale.Crop
//                                                )
//                                            }
//
//                                            is AsyncImagePainter.State.Loading -> {
//                                                CircularProgressIndicator(modifier = Modifier.size(30.dp))
//                                            }
//
//                                            else -> {
//                                                Image(
//                                                    painter = painterResource(R.drawable.minilogogc),
//                                                    contentDescription = "Fallback Image",
//                                                    modifier = Modifier.size(50.dp)
//                                                )
//                                            }
//                                        }
//                                    }
//
//                                    Spacer(modifier = Modifier.width(12.dp))
//
//                                    // Информация справа
//                                    Column(
//                                        verticalArrangement = Arrangement.SpaceEvenly,
//                                        modifier = Modifier.fillMaxHeight()
//                                    ) {
//                                        Text(
//                                            text = product.product_name,
//                                            fontSize = 16.sp,
//                                            fontWeight = FontWeight.Bold,
//                                            maxLines = 1,
//                                            overflow = TextOverflow.Ellipsis
//                                        )
//
//                                        Text(
//                                            text = "Кол-во: ${product.quantity_in_stock}",
//                                            fontSize = 14.sp
//                                        )
//
//                                        Text(
//                                            text = "${product.price} ₽",
//                                            fontSize = 16.sp,
//                                            fontWeight = FontWeight.Bold,
//                                            color = Color(0xFFC21631)
//                                        )
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }
}

