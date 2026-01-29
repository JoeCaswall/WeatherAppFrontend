package com.mobileappsfrontend.weatherapp.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.mobileappsfrontend.weatherapp.data.local.preferences.UserPreferences
import com.mobileappsfrontend.weatherapp.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch

// This handles UI state, validation, loading, and calling the use case.
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {

    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var loginSuccess by mutableStateOf(false)

    fun onLoginClicked() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val result = loginUseCase(username, password)

            isLoading = false

            if (result.isSuccess) {
                val jwt = result.getOrNull()!! // your use case returns the token
                userPreferences.saveJwt(jwt)
                loginSuccess = true
            } else {
                errorMessage = result.exceptionOrNull()?.message
            }
        }
    }
}
