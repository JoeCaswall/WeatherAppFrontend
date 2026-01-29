package com.mobileappsfrontend.weatherapp.domain.usecase

import com.mobileappsfrontend.weatherapp.data.model.CurrentWeatherResponse
import com.mobileappsfrontend.weatherapp.domain.repository.WeatherRepository

class GetCurrentWeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double): CurrentWeatherResponse {
        println("UseCase: invoking repository.getCurrentWeather($lat, $lon)")
        val result = repository.getCurrentWeather(lat, lon)
        println("UseCase: repository returned successfully")
        return result
    }
}
