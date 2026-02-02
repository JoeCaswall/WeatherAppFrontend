package com.mobileappsfrontend.weatherapp.data.repository

import com.mobileappsfrontend.weatherapp.data.api.SearchApi
import com.mobileappsfrontend.weatherapp.data.model.FavouriteLocationDto
import com.mobileappsfrontend.weatherapp.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi
) : SearchRepository {
    override suspend fun searchLocations(query: String, jwt: String): List<FavouriteLocationDto> {
        return searchApi.searchLocations(query, "Bearer $jwt")
    }
}
