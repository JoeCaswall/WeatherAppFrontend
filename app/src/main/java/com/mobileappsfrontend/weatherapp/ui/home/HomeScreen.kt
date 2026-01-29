package com.mobileappsfrontend.weatherapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobileappsfrontend.weatherapp.data.model.CurrentWeatherResponse

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    println("HomeScreen: Composable STARTED")
    val weather = viewModel.uiState
    val errorMessage = viewModel.errorMessage
    val isLoading = viewModel.isLoading

    when {
        isLoading -> CircularProgressIndicator()
        errorMessage != null -> {
            println("HomeScreen: ERROR → $errorMessage")
            Text("Error: $errorMessage", color = Color.Red)
        }
        weather != null -> WeatherCard(weather)
    }
}

@Composable
fun WeatherCard(weather: CurrentWeatherResponse) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Temperature: ${weather.temp}°C")
            Text("Feels like: ${weather.feelsLikeTemp}°C")
            Text("Wind: ${weather.windDirection} ${weather.windSpeedKmh} km/h")
            Text("Humidity: ${weather.humidity}%")
            Text("Air Quality: ${weather.airQuality}")
            Text("Sunrise: ${weather.sunriseTime}")
            Text("Sunset: ${weather.sunsetTime}")
            Text("Precipitation: ${weather.precipitation} mm")
        }
    }
}