package com.mobileappsfrontend.weatherapp.data.api

import com.mobileappsfrontend.weatherapp.data.model.DefaultLocationResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface DefaultLocationApi {
    @GET("/api/user/default-location")
    suspend fun getCurrentDefaultLocation(
        @Header("Authorization") authHeader: String
    ) : DefaultLocationResponse
}