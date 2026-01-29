package com.mobileappsfrontend.weatherapp.domain.usecase

import com.mobileappsfrontend.weatherapp.data.model.DefaultLocationResponse
import com.mobileappsfrontend.weatherapp.domain.repository.UserRepository
import javax.inject.Inject

class GetDefaultLocationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): DefaultLocationResponse =
        userRepository.getDefaultLocation()
}