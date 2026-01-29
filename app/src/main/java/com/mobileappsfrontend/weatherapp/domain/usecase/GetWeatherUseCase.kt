package com.mobileappsfrontend.weatherapp.domain.usecase

import com.mobileappsfrontend.weatherapp.data.repository.WeatherRepositoryImpl

class GetCurrentWeatherUseCase(
    private val repository: WeatherRepositoryImpl
) {
    suspend operator fun invoke() = repository.getCurrentWeather()
}
