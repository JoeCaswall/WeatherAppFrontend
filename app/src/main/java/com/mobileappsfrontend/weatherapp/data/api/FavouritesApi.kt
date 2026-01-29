package com.mobileappsfrontend.weatherapp.data.api

import com.mobileappsfrontend.weatherapp.data.model.FavouriteLocationDto
import retrofit2.http.GET
import retrofit2.http.Header

interface FavouritesApi {
    @GET("/api/favourites")
    suspend fun getFavourites(@Header("Authorization") authHeader: String): List<FavouriteLocationDto>
}