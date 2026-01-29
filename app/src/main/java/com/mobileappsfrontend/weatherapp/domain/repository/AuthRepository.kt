package com.mobileappsfrontend.weatherapp.domain.repository

import com.mobileappsfrontend.weatherapp.data.model.LoginResponse

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<String>
}
