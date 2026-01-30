package com.mobileappsfrontend.weatherapp.ui.favourites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.mobileappsfrontend.weatherapp.data.model.CurrentWeatherResponse
import com.mobileappsfrontend.weatherapp.data.model.FavouriteLocationDto
import com.mobileappsfrontend.weatherapp.domain.repository.WeatherRepository

@Composable
fun FavouritesScreen(
    onLocationClick: (FavouriteLocationDto) -> Unit,
    viewModel: FavouritesViewModel = hiltViewModel()
) {
    val favourites = viewModel.favourites.value
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value

    LaunchedEffect(Unit) { viewModel.loadFavourites() }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            isLoading -> CircularProgressIndicator()
            errorMessage != null -> Text("Error: $errorMessage")
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(favourites.size) { idx ->
                        val fav = favourites[idx]
                        FavouriteCard(fav, onClick = { onLocationClick(fav) })
                    }
                }
            }
        }
    }
}

@Composable
fun FavouriteCard(
    fav: FavouriteLocationDto,
    onClick: () -> Unit,
    viewModel: FavouritesViewModel = hiltViewModel()
) {
    var weather by remember(fav.latitude to fav.longitude) { mutableStateOf<CurrentWeatherResponse?>(null) }
    var isLoading by remember(fav.latitude to fav.longitude) { mutableStateOf(true) }
    var error by remember(fav.latitude to fav.longitude) { mutableStateOf<String?>(null) }

    LaunchedEffect(fav.latitude, fav.longitude) {
        isLoading = true
        error = null
        try {
            weather = viewModel.weatherRepository.getCurrentWeather(fav.latitude, fav.longitude)
            println("Sending current weather request for city: ${fav.cityName}, lat: ${fav.latitude}, longitude: ${fav.longitude}")
        } catch (e: Exception) {
            error = e.message
        } finally {
            isLoading = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.size(24.dp))
                error != null -> Text("Error", color = androidx.compose.ui.graphics.Color.Red)
                weather != null -> {
                    val iconCode = weather!!.conditions.icon
                    val iconUrl = "https://www.weatherbit.io/static/img/icons/${iconCode}.png"
                    AsyncImage(
                        model = iconUrl,
                        contentDescription = "Weather icon",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(fav.cityName)
                    Text("Temp: ${weather!!.temp}°C")
                    Text("Precip: ${weather!!.precipitation} mm")
                }
            }
        }
    }
        }
}
