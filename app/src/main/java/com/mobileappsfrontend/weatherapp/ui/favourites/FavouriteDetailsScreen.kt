package com.mobileappsfrontend.weatherapp.ui.favourites

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mobileappsfrontend.weatherapp.data.model.CurrentWeatherResponse
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FavouriteDetailsScreen(
    lat: Double,
    lon: Double,
    onBack: () -> Unit,
    viewModel: FavouritesViewModel = hiltViewModel()
) {
    val weatherState = remember { mutableStateOf<CurrentWeatherResponse?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(lat, lon) {
        isLoading.value = true
        errorMessage.value = null
        try {
            val result = viewModel.weatherRepositoryGetter.getCurrentWeather(lat, lon)
            weatherState.value = result
        } catch (e: Exception) {
            errorMessage.value = e.message
        } finally {
            isLoading.value = false
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = onBack, modifier = Modifier.padding(8.dp)) {
                Text("Back to Favourites")
            }
            when {
                isLoading.value -> Text("Loading...")
                errorMessage.value != null -> Text("Error: ${errorMessage.value}")
                weatherState.value != null -> WeatherDetailsCard(weatherState.value!!)
            }
        }
    }
}

@Composable
fun WeatherDetailsCard(weather: CurrentWeatherResponse) {
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
