package com.mobileappsfrontend.weatherapp.domain.repository

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<String>
}
