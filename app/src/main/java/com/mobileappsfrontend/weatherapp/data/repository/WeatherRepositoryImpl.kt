package com.mobileappsfrontend.weatherapp.data.repository

import com.mobileappsfrontend.weatherapp.data.api.WeatherApi
import com.mobileappsfrontend.weatherapp.data.api.DefaultLocationApi
import com.mobileappsfrontend.weatherapp.data.local.preferences.UserPreferences
import com.mobileappsfrontend.weatherapp.data.model.CurrentWeatherResponse
import com.mobileappsfrontend.weatherapp.data.model.DefaultLocationResponse
import com.mobileappsfrontend.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.first
class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val defaultLocationApi: DefaultLocationApi,
    private val prefs: UserPreferences
) : WeatherRepository {
    override suspend fun getCurrentWeather(lat: Double, lon: Double): CurrentWeatherResponse {
        val jwt = prefs.jwtFlow.first()
        return api.getCurrentWeather(lat, lon, "Bearer $jwt")
    }

    override suspend fun getDefaultLocation(): DefaultLocationResponse {
        val jwt = prefs.jwtFlow.first()
        return defaultLocationApi.getCurrentDefaultLocation("Bearer $jwt")
    }
}