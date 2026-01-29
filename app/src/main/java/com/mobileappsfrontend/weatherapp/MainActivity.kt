package com.mobileappsfrontend.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.mobileappsfrontend.weatherapp.data.api.RetrofitInstance
import com.mobileappsfrontend.weatherapp.data.api.RetrofitInstance.weatherApi
import com.mobileappsfrontend.weatherapp.data.local.preferences.UserPreferences
import com.mobileappsfrontend.weatherapp.data.local.preferences.dataStore
import com.mobileappsfrontend.weatherapp.data.repository.AuthRepositoryImpl
import com.mobileappsfrontend.weatherapp.data.repository.WeatherRepositoryImpl
import com.mobileappsfrontend.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import com.mobileappsfrontend.weatherapp.domain.usecase.LoginUseCase
import com.mobileappsfrontend.weatherapp.ui.home.HomeViewModel
import com.mobileappsfrontend.weatherapp.ui.login.LoginViewModel
import com.mobileappsfrontend.weatherapp.ui.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        println("MainActivity: onCreate STARTED")
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            AppNavHost(
                navController = navController
            )
        }
    }
}
