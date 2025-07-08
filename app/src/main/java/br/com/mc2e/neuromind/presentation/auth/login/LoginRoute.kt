package br.com.mc2e.neuromind.presentation.auth.login

import LoginScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.mc2e.neuromind.presentation.shared.navigation.Screen

@Composable
fun LoginRoute(navController: NavController) {
    val viewModel: LoginViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is LoginUiEvent.NavigateToHome -> navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }

                is LoginUiEvent.NavigateToRegister -> navController.navigate(
                    Screen.Register.createRoute()
                ) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }

                else -> {}
            }
        }
    }

    LoginScreen(
        uiState = (uiState as? LoginUiState.Form) ?: LoginUiState.Form(),
        onEvent = viewModel::onEvent,
        uiEventFlow = viewModel.uiEvent
    )
}