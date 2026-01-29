package com.mobileappsfrontend.weatherapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.mobileappsfrontend.weatherapp.data.model.CurrentWeatherResponse
import com.mobileappsfrontend.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    var uiState by mutableStateOf<CurrentWeatherResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        println("HomeViewModel: INIT")
        loadWeatherForDefaultLocation()
    }

    private fun loadWeatherForDefaultLocation() {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null
                // Fetch default location
                val location = repository.getDefaultLocation()
                // Fetch weather for that location
                val result = repository.getCurrentWeather(location.latitude, location.longitude)
                uiState = result
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }
}
