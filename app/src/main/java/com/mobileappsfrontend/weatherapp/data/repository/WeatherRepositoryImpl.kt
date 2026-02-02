package com.mobileappsfrontend.weatherapp.data.repository

import com.mobileappsfrontend.weatherapp.data.api.WeatherApi
import com.mobileappsfrontend.weatherapp.data.api.DefaultLocationApi
import com.mobileappsfrontend.weatherapp.data.local.dao.CurrentWeatherDao
import com.mobileappsfrontend.weatherapp.data.local.entity.CurrentWeatherEntity
import com.mobileappsfrontend.weatherapp.data.local.preferences.UserPreferences
import com.mobileappsfrontend.weatherapp.data.model.CurrentWeatherResponse
import com.mobileappsfrontend.weatherapp.data.model.DefaultLocationResponse
import com.mobileappsfrontend.weatherapp.data.model.WeatherbitCurrentConditionsResponse
import com.mobileappsfrontend.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val defaultLocationApi: DefaultLocationApi,
    private val prefs: UserPreferences,
    private val weatherDao: CurrentWeatherDao
) : WeatherRepository {
    override suspend fun getCachedWeather(): CurrentWeatherResponse? {
        val cached = weatherDao.getCachedWeather()
        return cached?.toDomainModel()
        }

    override suspend fun getCurrentWeather(lat: Double, lon: Double): CurrentWeatherResponse {
            return try {
                val jwt = prefs.jwtFlow.first()
                val response = api.getCurrentWeather(lat, lon, "Bearer $jwt")
                // Save to cache
                weatherDao.upsertWeather(response.toEntity())
                response
            } catch (e: Exception) {
                throw e // Or return a default/fallback value
            }
        }


    override suspend fun getDefaultLocation(): DefaultLocationResponse {
        val jwt = prefs.jwtFlow.first()
        return defaultLocationApi.getCurrentDefaultLocation("Bearer $jwt")
        }
    fun CurrentWeatherEntity.toDomainModel(): CurrentWeatherResponse {
        return CurrentWeatherResponse(
            cityName = cityName,
            temp = temp,
            feelsLikeTemp = feelsLikeTemp,
            windDirection = windDirection,
            windSpeedKmh = windSpeedKmh,
            conditions = WeatherbitCurrentConditionsResponse(
                description = conditionsDescription,
                icon = conditionsIcon,
            ),
            airQuality = airQuality,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime,
            humidity = humidity,
            precipitation = precipitation
        )
    }

    fun CurrentWeatherResponse.toEntity(): CurrentWeatherEntity {
        return CurrentWeatherEntity(
            cityName = cityName,
            temp = temp,
            feelsLikeTemp = feelsLikeTemp,
            windDirection = windDirection,
            windSpeedKmh = windSpeedKmh,
            conditionsIcon = conditions.icon,
            conditionsDescription = conditions.description,
            airQuality = airQuality,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime,
            humidity = humidity,
            precipitation = precipitation,
            //TODO: Implement check for stale data and force refresh if it's old
            lastUpdatedEpochMillis = System.currentTimeMillis()
        )
    }
}

