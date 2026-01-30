package com.mobileappsfrontend.weatherapp.data.repository

import com.mobileappsfrontend.weatherapp.data.api.FavouritesApi
import com.mobileappsfrontend.weatherapp.data.local.preferences.UserPreferences
import com.mobileappsfrontend.weatherapp.data.model.FavouriteLocationDto
import com.mobileappsfrontend.weatherapp.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val api: FavouritesApi,
    private val prefs: UserPreferences
): FavouriteRepository {
    override suspend fun getFavourites() : List<FavouriteLocationDto> {
        val token = prefs.jwtFlow.first()
        return api.getFavourites(authHeader = "Bearer $token")
    }
}