package com.mobileappsfrontend.weatherapp.data.model

data class FavouriteLocationDto(
    val cityName: String,
    val stateCode: String,
    val countryCode: String,
    val countryFull: String,
    val lat: Double,
    val lon: Double
)