package com.mobileappsfrontend.weatherapp.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AuthScreen(
    onAuthSuccess: () -> Unit
) {
    var isLogin by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = { isLogin = true },
                enabled = !isLogin,
                modifier = Modifier.weight(1f)
            ) { Text("Login") }
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = { isLogin = false },
                enabled = isLogin,
                modifier = Modifier.weight(1f)
            ) { Text("Sign Up") }
        }
        Spacer(Modifier.height(32.dp))
        if (isLogin) {
            LoginScreen(viewModel = hiltViewModel(), onLoginSuccess = onAuthSuccess)
        } else {
            SignupScreen(viewModel = hiltViewModel(), onSignupSuccess = onAuthSuccess)
        }
    }
}
