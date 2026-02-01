package com.mobileappsfrontend.weatherapp.data.api

import com.mobileappsfrontend.weatherapp.data.model.LoginRequest
import com.mobileappsfrontend.weatherapp.data.model.LoginResponse
import com.mobileappsfrontend.weatherapp.data.model.SignupRequest
import com.mobileappsfrontend.weatherapp.data.model.SignupResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("api/auth/signup")
    suspend fun signup(@Body request: SignupRequest): String
}
