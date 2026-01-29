package com.mobileappsfrontend.weatherapp.domain.repository

import com.mobileappsfrontend.weatherapp.data.model.CurrentWeatherResponse

interface WeatherRepository {
    suspend fun getCurrentWeather(): CurrentWeatherResponse
}
