package com.mobileappsfrontend.weatherapp.data.repository

import com.mobileappsfrontend.weatherapp.data.api.AuthApi
import com.mobileappsfrontend.weatherapp.data.local.preferences.UserPreferences
import com.mobileappsfrontend.weatherapp.data.model.LoginRequest
import com.mobileappsfrontend.weatherapp.domain.repository.AuthRepository

// Where Retrofit + DataStore + DTOs actually get used.
class AuthRepositoryImpl(
    private val api: AuthApi,
    private val prefs: UserPreferences
) : AuthRepository {

    override suspend fun login(username: String, password: String): Result<Unit> {
        return try {
            val response = api.login(LoginRequest(username, password))
            prefs.saveJwt(response.token)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
