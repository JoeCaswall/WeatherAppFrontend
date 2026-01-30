package com.mobileappsfrontend.weatherapp.domain.repository

import com.mobileappsfrontend.weatherapp.data.model.DefaultLocationResponse

interface UserRepository {
    suspend fun getDefaultLocation(): DefaultLocationResponse
}