package com.mobileappsfrontend.weatherapp.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.mobileappsfrontend.weatherapp.domain.usecase.SignupUseCase
import kotlinx.coroutines.launch

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase
) : ViewModel() {
    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var signupSuccess by mutableStateOf(false)

    fun onSignupClicked() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            val result = signupUseCase(username, email, password)
            isLoading = false
            if (result.isSuccess) {
                signupSuccess = true
            } else {
                errorMessage = result.exceptionOrNull()?.message
            }
        }
    }
}
