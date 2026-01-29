package com.mobileappsfrontend.weatherapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileappsfrontend.weatherapp.data.model.CurrentWeatherResponse
import com.mobileappsfrontend.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : ViewModel() {

    var uiState by mutableStateOf<CurrentWeatherResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        println("HomeViewModel: INIT")
        loadWeather()
    }

    private fun loadWeather() {
        viewModelScope.launch {
            println("HomeViewModel: loadWeather() started")
            try {
                isLoading = true
                errorMessage = null
                println("HomeViewModel: calling use case…")

                val result = getCurrentWeatherUseCase()
                println("HomeViewModel: use case returned: $result")

                uiState = result
            } catch (e: Exception) {
                println("HomeViewModel: ERROR → ${e.message}")
                errorMessage = e.message
            } finally {
                println("HomeViewModel: loadWeather() finished")
                isLoading = false
            }
        }
    }
}
