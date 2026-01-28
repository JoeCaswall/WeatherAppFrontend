package com.mobileappsfrontend.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.mobileappsfrontend.weatherapp.data.api.RetrofitInstance
import com.mobileappsfrontend.weatherapp.data.local.preferences.UserPreferences
import com.mobileappsfrontend.weatherapp.data.local.preferences.dataStore
import com.mobileappsfrontend.weatherapp.data.repository.AuthRepositoryImpl
import com.mobileappsfrontend.weatherapp.domain.usecase.LoginUseCase
import com.mobileappsfrontend.weatherapp.ui.login.LoginViewModel
import com.mobileappsfrontend.weatherapp.ui.navigation.AppNavHost

class MainActivity : ComponentActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val dataStore = applicationContext.dataStore
        val prefs = UserPreferences(dataStore)
        val api = RetrofitInstance.authApi
        val repo = AuthRepositoryImpl(api, prefs)
        val useCase = LoginUseCase(repo)


        loginViewModel = LoginViewModel(useCase)

        setContent {
            val navController = rememberNavController()

            AppNavHost(
                navController = navController,
                loginViewModel = loginViewModel
            )
        }
    }
}

