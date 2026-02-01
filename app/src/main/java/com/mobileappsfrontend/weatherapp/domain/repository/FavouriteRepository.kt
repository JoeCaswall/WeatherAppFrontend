package com.mobileappsfrontend.weatherapp.domain.repository

import com.mobileappsfrontend.weatherapp.data.model.FavouriteLocationDto

interface FavouriteRepository {
    suspend fun getFavourites(): List<FavouriteLocationDto>
    suspend fun addToFavourites(location: FavouriteLocationDto)
    suspend fun deleteFavourite(id: Int)
}