package com.mobileappsfrontend.weatherapp.data.api

import androidx.room.Query
import com.mobileappsfrontend.weatherapp.data.model.CurrentWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface WeatherApi {
    @GET("/api/weather/current/coordinates")
    suspend fun getCurrentWeather(
        @Header("Authorization") authHeader: String
    ): CurrentWeatherResponse
}