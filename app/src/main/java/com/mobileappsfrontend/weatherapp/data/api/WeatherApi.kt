package com.mobileappsfrontend.weatherapp.data.api

import com.mobileappsfrontend.weatherapp.data.model.CurrentWeatherResponse
import retrofit2.http.Query
import retrofit2.http.GET
import retrofit2.http.Header

interface WeatherApi {
    @GET("/api/weather/current/coordinates")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Header("Authorization") authHeader: String
    ): CurrentWeatherResponse
}