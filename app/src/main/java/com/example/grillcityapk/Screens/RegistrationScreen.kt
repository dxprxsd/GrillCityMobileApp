package com.example.grillcityapk.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.grillcityapk.MainViewModel
import com.example.grillcityapk.R

@Composable
fun RegistrationScreen(navController: NavHostController, viewModel: MainViewModel) {
    var fname by remember { mutableStateOf("") }
    var sname by remember { mutableStateOf("") }
    var patronumic by remember { mutableStateOf("") }
    var phonenumber by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val registrationResult by viewModel.registrationResult


    LaunchedEffect(registrationResult) {
        if (registrationResult == "Success") {
            navController.navigate("login_screen")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFA7262F))
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        // Верхний блок с логотипом и заголовком
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Регистрация",
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

        Spacer(modifier = Modifier.height(8.dp))

        // Основной белый блок с полями авторизации
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp) // Отступы по бокам
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()) // Добавляем скролл если контент не помещается
            ) {
                // Поле фамилия
                Text(
                    text = "Фамилия",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 4.dp),
                    color = Color.Black
                )
                OutlinedTextField(
                    value = sname,
                    onValueChange = { sname = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFD9D9D9), RoundedCornerShape(8.dp))
                        .border(BorderStroke(1.dp, Color(0xFFC21631)), RoundedCornerShape(8.dp))
                        .testTag("RegFNameField"),
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, color = Color.Black),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Поле имя
                Text(
                    text = "Имя",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 4.dp),
                    color = Color.Black
                )
                OutlinedTextField(
                    value = fname,
                    onValueChange = { fname = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFD9D9D9), RoundedCornerShape(8.dp))
                        .border(BorderStroke(1.dp, Color(0xFFC21631)), RoundedCornerShape(8.dp))
                        .testTag("RegSNameField"),
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, color = Color.Black),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Поле отчество
                Text(
                    text = "Отчество",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 4.dp),
                    color = Color.Black
                )
                OutlinedTextField(
                    value = patronumic,
                    onValueChange = { patronumic = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFD9D9D9), RoundedCornerShape(8.dp))
                        .border(BorderStroke(1.dp, Color(0xFFC21631)), RoundedCornerShape(8.dp))
                        .testTag("RegPatronumicField"),
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, color = Color.Black),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Поле телефон
                Text(
                    text = "Телефон",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 4.dp),
                    color = Color.Black
                )
                OutlinedTextField(
                    value = phonenumber,
                    onValueChange = {
                        phonenumber = it.filter { c -> c.isDigit() }
                        if (phonenumber.length > 11) phonenumber = phonenumber.take(11)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFD9D9D9), RoundedCornerShape(8.dp))
                        .border(BorderStroke(1.dp, Color(0xFFC21631)), RoundedCornerShape(8.dp))
                        .testTag("RegNumberField"),
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, color = Color.Black),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Поле логин
                Text(
                    text = "Логин",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 4.dp),
                    color = Color.Black
                )
                OutlinedTextField(
                    value = login,
                    onValueChange = { login = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFD9D9D9), RoundedCornerShape(8.dp))
                        .border(BorderStroke(1.dp, Color(0xFFC21631)), RoundedCornerShape(8.dp)),
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, color = Color.Black),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Поле пароль
                Text(
                    text = "Пароль",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 4.dp),
                    color = Color.Black
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("RegPasswordField")
                        .background(Color(0xFFD9D9D9), RoundedCornerShape(8.dp))
                        .border(BorderStroke(1.dp, Color(0xFFC21631)), RoundedCornerShape(8.dp)),
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, color = Color.Black),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Кнопка регистрации
                Button(
                    onClick = {
                        if (login.isBlank() || password.isBlank() || sname.isBlank() ||
                            fname.isBlank() || phonenumber.isBlank()) {
                            // Показать ошибку
                            return@Button
                        }
                        viewModel.registerUser(
                            login = login,
                            password = password,
                            surname = sname,
                            firstName = fname,
                            patronymic = patronumic.takeIf { it.isNotBlank() },
                            phoneNumber = phonenumber
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC21631)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Зарегистрироваться",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

}