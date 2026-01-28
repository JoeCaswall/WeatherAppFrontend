package com.mobileappsfrontend.weatherapp.data.api

import com.mobileappsfrontend.weatherapp.data.model.LoginRequest
import com.mobileappsfrontend.weatherapp.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}
