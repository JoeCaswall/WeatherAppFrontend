package com.mobileappsfrontend.weatherapp.data.api

import com.mobileappsfrontend.weatherapp.data.model.FavouriteLocationDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchApi {
    @GET("/api/locations/search")
    suspend fun searchLocations(
        @Query("query") query: String,
        @Header("Authorization") authHeader: String
    ): List<FavouriteLocationDto>
}
