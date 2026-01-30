package com.mobileappsfrontend.weatherapp.ui.favourites

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileappsfrontend.weatherapp.data.model.FavouriteLocationDto
import com.mobileappsfrontend.weatherapp.domain.repository.FavouriteRepository
import com.mobileappsfrontend.weatherapp.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val repository: FavouriteRepository,
    val weatherRepository: WeatherRepository
) : ViewModel() {
    val weatherRepositoryGetter: WeatherRepository
        get() = this.weatherRepository
    var favourites = mutableStateOf<List<FavouriteLocationDto>>(emptyList())
        private set
    var isLoading = mutableStateOf(false)
        private set
    var errorMessage = mutableStateOf<String?>(null)
        private set


    fun loadFavourites() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                favourites.value = repository.getFavourites()
            } catch (e: Exception) {
                errorMessage.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }
}
