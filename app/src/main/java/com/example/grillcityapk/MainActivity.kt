package com.example.grillcityapk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.grillcityapk.Screens.CartScreen
import com.example.grillcityapk.Screens.LoadScreen
import com.example.grillcityapk.Screens.MainScreen
import com.example.grillcityapk.Screens.LoginScreen
import com.example.grillcityapk.ui.theme.GrillCityApkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GrillCityApkTheme {
                val viewModel: MainViewModel = viewModel()
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "load_screen"
                ) {

                    composable("load_screen") {
                        LoadScreen(navController)
                    }
                    composable("main_screen") {
                        MainScreen(navController = navController, viewModel = viewModel)
                    }
                    composable("login_screen") {
                        LoginScreen(navController = navController, viewModel = viewModel)
                    }
                    composable("cart_screen") {
                        CartScreen(navController = navController, viewModel = viewModel)
                    }
                }
            }
        }
    }
}
