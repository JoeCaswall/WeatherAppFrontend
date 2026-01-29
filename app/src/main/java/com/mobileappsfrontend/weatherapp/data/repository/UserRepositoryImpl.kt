package com.mobileappsfrontend.weatherapp.data.repository

import com.mobileappsfrontend.weatherapp.data.api.DefaultLocationApi
import com.mobileappsfrontend.weatherapp.data.local.preferences.UserPreferences
import com.mobileappsfrontend.weatherapp.data.model.DefaultLocationResponse
import com.mobileappsfrontend.weatherapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: DefaultLocationApi,
    private val prefs: UserPreferences
) : UserRepository {

    override suspend fun getDefaultLocation(): DefaultLocationResponse {
        val token = prefs.jwtFlow.first()

        val dto = api.getCurrentDefaultLocation(
            authHeader = "Bearer $token"
        )

        return DefaultLocationResponse(
            cityName = dto.cityName,
            latitude = dto.latitude,
            longitude = dto.longitude
        )
    }
}
