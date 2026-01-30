package com.mobileappsfrontend.weatherapp.data.model

data class CurrentWeatherResponse(
    val cityName: String,
    val temp: Double,
    val feelsLikeTemp: Double,
    val windDirection: String,
    val windSpeedKmh: Double,
    val conditions: WeatherbitCurrentConditionsResponse,
    val airQuality: Int,
    val sunriseTime: String,
    val sunsetTime: String,
    val humidity: Int,
    val precipitation: Double
)
