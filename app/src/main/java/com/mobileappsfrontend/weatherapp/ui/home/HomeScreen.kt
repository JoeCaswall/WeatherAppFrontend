package com.mobileappsfrontend.weatherapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.material3.Card
import coil.compose.AsyncImage
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
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(2f/3f)
                .fillMaxHeight(1f/3f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val iconCode = weather.conditions.icon
                val iconUrl = "https://www.weatherbit.io/static/img/icons/${iconCode}.png"
                AsyncImage(
                    model = iconUrl,
                    contentDescription = "Weather icon",
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(weather.cityName)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Temperature: ${weather.temp}°C")
                Text("Feels like: ${weather.feelsLikeTemp}°C")
                Text("Wind: ${weather.windSpeedKmh} km/h ${weather.windDirection}")
                Text("Humidity: ${weather.humidity}%")
                Text("Air Quality: ${weather.airQuality}")
                Text("Sunrise: ${weather.sunriseTime}")
                Text("Sunset: ${weather.sunsetTime}")
                Text("Precipitation: ${weather.precipitation} mm")
            }
        }
    }
}