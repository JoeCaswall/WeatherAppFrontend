package com.mobileappsfrontend.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.mobileappsfrontend.weatherapp.data.api.RetrofitInstance
import com.mobileappsfrontend.weatherapp.data.local.preferences.UserPreferences
import com.mobileappsfrontend.weatherapp.data.local.preferences.dataStore
import com.mobileappsfrontend.weatherapp.data.repository.AuthRepositoryImpl
import com.mobileappsfrontend.weatherapp.domain.repository.WeatherRepository
import com.mobileappsfrontend.weatherapp.domain.usecase.LoginUseCase
import com.mobileappsfrontend.weatherapp.ui.home.HomeViewModel
import com.mobileappsfrontend.weatherapp.ui.login.LoginViewModel
import com.mobileappsfrontend.weatherapp.ui.navigation.AppNavHost

class MainActivity : ComponentActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Shared DataStore + Preferences
        val prefs = UserPreferences(applicationContext.dataStore)

        // Login dependencies
        val authApi = RetrofitInstance.authApi
        val authRepo = AuthRepositoryImpl(authApi, prefs)
        val loginUseCase = LoginUseCase(authRepo)
        loginViewModel = LoginViewModel(loginUseCase, prefs)

        // Home dependencies
        val weatherApi = RetrofitInstance.weatherApi
        val weatherRepo = WeatherRepository(weatherApi, prefs)
        homeViewModel = HomeViewModel(weatherRepo)

        setContent {
            val navController = rememberNavController()

            AppNavHost(
                navController = navController,
                loginViewModel = loginViewModel,
                homeViewModel = homeViewModel
            )
        }
    }
}
