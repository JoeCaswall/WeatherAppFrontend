package com.mobileappsfrontend.weatherapp.data.api

import com.mobileappsfrontend.weatherapp.data.model.FavouriteLocationDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface FavouritesApi {
    @GET("/api/favourites")
    suspend fun getFavourites(
        @Header("Authorization") authHeader: String
    ): List<FavouriteLocationDto>

    @POST("api/favourites")
    suspend fun addToFavourites(
        @Header("Authorization") authHeader: String,
        @Body req: FavouriteLocationDto)

    @DELETE("api/favourites/delete")
    suspend fun deleteFavourite(
        @Query("id") id: Int,
        @Header("Authorization") authHeader: String
    )
}