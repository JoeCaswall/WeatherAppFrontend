package com.mobileappsfrontend.weatherapp.domain.usecase

import com.mobileappsfrontend.weatherapp.data.model.CurrentWeatherResponse
import com.mobileappsfrontend.weatherapp.domain.repository.WeatherRepository

class GetCurrentWeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(): CurrentWeatherResponse
    { println("UseCase: invoking repository.getCurrentWeather()")
        val result = repository.getCurrentWeather()
        println("UseCase: repository returned successfully")
        return result
    }
}
