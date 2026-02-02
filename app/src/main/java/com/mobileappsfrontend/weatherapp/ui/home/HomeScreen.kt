package com.mobileappsfrontend.weatherapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.material3.Card
import coil.compose.AsyncImage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobileappsfrontend.weatherapp.data.model.CurrentWeatherResponse

@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavHostController) {
    println("HomeScreen: Composable STARTED")
    val weather = viewModel.uiState
    val errorMessage = viewModel.errorMessage
    val isLoading = viewModel.isLoading
    val searchResults = viewModel.searchResults
    val isSearchLoading = viewModel.isSearchLoading
    val searchError = viewModel.searchError
    val addFavouriteLoading = viewModel.addFavouriteLoading
    val addFavouriteError = viewModel.addFavouriteError

    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search for a location") },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.searchLocations(searchQuery) },
            enabled = searchQuery.isNotBlank() && !isSearchLoading,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search")
        }
        if (isSearchLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 8.dp))
        }
        if (searchError != null) {
            Text("Search error: $searchError", color = Color.Red)
        }
        if (searchResults.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text("Results:", modifier = Modifier.padding(bottom = 4.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                searchResults.forEach { location ->
                    Card(
                        modifier = Modifier
                            .widthIn(max = 400.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(location.cityName + ", " + location.stateCode + ", " + location.countryFull)
                                Text("Lat: ${location.latitude}, Lon: ${location.longitude}", color = Color.Gray)
                            }
                            Button(
                                onClick = {
                                    viewModel.addToFavourites(location) {
                                        navController.navigate("favourites")
                                    }
                                },
                                enabled = !addFavouriteLoading
                            ) {
                                Text("Add to Favourites")
                            }
                            if (addFavouriteLoading) {
                                CircularProgressIndicator(modifier = Modifier.padding(top = 8.dp))
                            }
                            if (addFavouriteError != null) {
                                Text("Add to favourites error: $addFavouriteError", color = Color.Red)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Weather section
        when {
            isLoading -> CircularProgressIndicator()
            errorMessage != null -> {
                println("HomeScreen: ERROR → $errorMessage")
                Text("Error: $errorMessage", color = Color.Red)
            }
            weather != null -> WeatherCard(weather)
        }
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
                .fillMaxHeight(1f/2f)
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