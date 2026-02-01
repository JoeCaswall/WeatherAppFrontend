package com.mobileappsfrontend.weatherapp.domain.usecase

import com.mobileappsfrontend.weatherapp.data.model.LoginResponse
import com.mobileappsfrontend.weatherapp.domain.repository.AuthRepository

// Gives a clean business action. The ViewModel calls this instead of
// calling the repository directly.
class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<String> {
        return repository.login(email, password)
    }
}
