package com.mobileappsfrontend.weatherapp.domain.usecase

import com.mobileappsfrontend.weatherapp.domain.repository.AuthRepository

// Gives a clean, testable business action. The ViewModel calls this instead of
// calling the repository directly.
class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return repository.login(email, password)
    }
}
