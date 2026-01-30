package com.mobileappsfrontend.weatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class CurrentWeatherEntity(
    @PrimaryKey
    val cityName: String,

    val temp: Double,
    val feelsLikeTemp: Double,

    val windDirection: String,
    val windSpeedKmh: Double,

    val airQuality: Int,
    val sunriseTime: String,
    val sunsetTime: String,
    val humidity: Int,
    val precipitation: Double,

    // Flattened WeatherbitCurrentConditions object from DTO
    val conditionsCode: Int,
    val conditionsIcon: String,
    val conditionsDescription: String
)
