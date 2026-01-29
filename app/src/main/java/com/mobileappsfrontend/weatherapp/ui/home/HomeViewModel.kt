package com.mobileappsfrontend.weatherapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileappsfrontend.weatherapp.data.model.CurrentWeatherResponse
import com.mobileappsfrontend.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    var uiState by mutableStateOf<CurrentWeatherResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadWeather()
    }

    private fun loadWeather() {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null
                uiState = weatherRepository.getCurrentWeather()
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }
}
