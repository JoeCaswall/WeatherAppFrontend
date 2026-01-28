package com.mobileappsfrontend.weatherapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mobileappsfrontend.weatherapp.ui.home.HomeScreen
import com.mobileappsfrontend.weatherapp.ui.login.LoginScreen
import com.mobileappsfrontend.weatherapp.ui.login.LoginViewModel

@Composable
fun AppNavHost(navController: NavHostController, loginViewModel: LoginViewModel) {
    NavHost(
        navController, startDestination = "login") {

        composable("login") {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = { navController.navigate("home") }
            )
        }

        composable("home") {
            HomeScreen()
        }
    }
}
