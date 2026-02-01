package com.mobileappsfrontend.weatherapp.data.model

 data class FavouriteLocationDto(
    val id: Int,
    val cityName: String,
    val stateCode: String,
    val countryCode: String,
    val countryFull: String,
    val latitude: Double,
    val longitude: Double
)