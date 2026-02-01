package com.mobileappsfrontend.weatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class CurrentWeatherEntity(
    @PrimaryKey
    // Same ID means cache only ever has one item in it and so can be referenced offline
    // (no defaultLocation backend call)
    val id: Int = 0,
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
    val conditionsIcon: String,
    val conditionsDescription: String,

    val lastUpdatedEpochMillis: Long
)
