package com.mobileappsfrontend.weatherapp.domain.repository

import com.mobileappsfrontend.weatherapp.data.model.CurrentWeatherResponse

import com.mobileappsfrontend.weatherapp.data.model.DefaultLocationResponse

interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double, lon: Double): CurrentWeatherResponse
    suspend fun getDefaultLocation(): DefaultLocationResponse
}
