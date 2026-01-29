package com.mobileappsfrontend.weatherapp.data.repository

import com.mobileappsfrontend.weatherapp.data.api.WeatherApi
import com.mobileappsfrontend.weatherapp.data.local.preferences.UserPreferences
import com.mobileappsfrontend.weatherapp.data.model.CurrentWeatherResponse
import com.mobileappsfrontend.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.first

class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val prefs: UserPreferences
) : WeatherRepository {
    override suspend fun getCurrentWeather(): CurrentWeatherResponse {
        println("Repo: waiting for JWT…")
        val jwt = prefs.jwtFlow.first()
        println("Repo: JWT = $jwt")

        println("Repo: calling API…")
        val response = api.getCurrentWeather("Bearer $jwt")
        println("Repo: API returned")

        return response
    }

}