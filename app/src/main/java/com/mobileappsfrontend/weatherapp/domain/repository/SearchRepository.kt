package com.mobileappsfrontend.weatherapp.domain.repository

import com.mobileappsfrontend.weatherapp.data.model.FavouriteLocationDto

interface SearchRepository {
    suspend fun searchLocations(query: String, jwt: String): List<FavouriteLocationDto>
}
