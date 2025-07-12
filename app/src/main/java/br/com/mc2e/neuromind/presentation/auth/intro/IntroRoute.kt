package br.com.mc2e.neuromind.presentation.auth.intro

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mc2e.neuromind.presentation.shared.navigation.Screen

@Composable
fun IntroRoute(navController: NavHostController) {
    val viewModel: IntroViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                IntroUiEvent.NavigateToLogin -> {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Intro.route) { inclusive = true }
                    }
                }

                IntroUiEvent.NavigateToRegister -> {
                    navController.navigate(
                        Screen.Register.createRoute()
                    ) {
                        popUpTo(Screen.Intro.route) { inclusive = true }
                    }
                }
            }
        }
    }

    IntroScreen(
        onEvent = viewModel::onEvent
    )
}