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
import com.mobileappsfrontend.weatherapp.domain.repository.SearchRepository
import com.mobileappsfrontend.weatherapp.data.local.preferences.UserPreferences
import com.mobileappsfrontend.weatherapp.data.model.FavouriteLocationDto
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@HiltViewModel

class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val searchRepository: SearchRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    var uiState by mutableStateOf<CurrentWeatherResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set


    // Search state
    var searchResults by mutableStateOf<List<FavouriteLocationDto>>(emptyList())
        private set
    var isSearchLoading by mutableStateOf(false)
        private set
    var searchError by mutableStateOf<String?>(null)
        private set

    init {
        println("HomeViewModel: INIT")
        loadWeatherForDefaultLocation()
    }
    fun searchLocations(query: String) {
        viewModelScope.launch {
            isSearchLoading = true
            searchError = null
            try {
                val jwt = userPreferences.jwtFlow.firstOrNull()
                if (jwt.isNullOrBlank()) {
                    searchError = "Not authenticated"
                    searchResults = emptyList()
                    isSearchLoading = false
                    return@launch
                }
                val results = searchRepository.searchLocations(query, jwt)
                searchResults = results
            } catch (e: Exception) {
                searchError = e.message
                searchResults = emptyList()
            } finally {
                isSearchLoading = false
            }
        }
    }

    private fun loadWeatherForDefaultLocation() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            // Try to load from cache first
            val cached = repository.getCachedWeather()
            var cacheShown = false
            if (cached != null) {
                uiState = cached
                cacheShown = true
            }
                // Then try to update from network (if possible)
            try {
                // Grab default location
                val location = repository.getDefaultLocation()
                // Fetch weather for that location
                val result = repository.getCurrentWeather(
                    location.latitude,
                    location.longitude
                )
                uiState = result
            } catch (e: Exception) {
                if (!cacheShown) {
                    errorMessage = e.message
                    uiState = null
                }
                // else: keep showing cached data, don't show error
            } finally {
                isLoading = false
            }
        }
    }
}
