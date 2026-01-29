package com.mobileappsfrontend.weatherapp.data.repository

import com.mobileappsfrontend.weatherapp.data.api.WeatherApi
import com.mobileappsfrontend.weatherapp.data.local.preferences.UserPreferences
import com.mobileappsfrontend.weatherapp.data.model.CurrentWeatherResponse
import kotlinx.coroutines.flow.first

class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val prefs: UserPreferences
) {

    suspend fun getCurrentWeather(): CurrentWeatherResponse {
        val jwt = prefs.jwtFlow.first() ?: error("JWT missing")
        return api.getCurrentWeather("Bearer $jwt")
    }
}