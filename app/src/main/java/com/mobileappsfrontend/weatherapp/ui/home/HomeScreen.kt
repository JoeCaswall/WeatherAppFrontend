package com.mobileappsfrontend.weatherapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.mobileappsfrontend.weatherapp.R
import com.mobileappsfrontend.weatherapp.data.model.CurrentWeatherResponse
import com.mobileappsfrontend.weatherapp.databinding.FragmentSecondBinding

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val weather = viewModel.uiState

    if (weather == null) {
        CircularProgressIndicator()
        return
    }

    WeatherCard(weather)
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