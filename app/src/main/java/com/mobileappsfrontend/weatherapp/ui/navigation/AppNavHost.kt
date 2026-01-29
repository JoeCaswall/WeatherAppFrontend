package com.mobileappsfrontend.weatherapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mobileappsfrontend.weatherapp.ui.home.HomeScreen
import com.mobileappsfrontend.weatherapp.ui.home.HomeViewModel
import com.mobileappsfrontend.weatherapp.ui.login.LoginScreen
import com.mobileappsfrontend.weatherapp.ui.login.LoginViewModel

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    println("NAVHOST: AppNavHost STARTED")
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = { navController.navigate("home") }
            )
        }
        composable("home") {
            println("NAVHOST: Entered home route")
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(viewModel = homeViewModel)
        }
    }
}
