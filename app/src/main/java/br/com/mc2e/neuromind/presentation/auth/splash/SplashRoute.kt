package br.com.mc2e.neuromind.presentation.auth.splash

import SplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.mc2e.neuromind.presentation.navigation.Screen

@Composable
fun SplashRoute(navController: NavController) {
    val viewModel: SplashViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                SplashUiEvent.NavigateToHome -> {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }

                SplashUiEvent.NavigateToIntro -> {
                    navController.navigate(Screen.Intro.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }

                SplashUiEvent.NavigateToLogin -> {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }

               is SplashUiEvent.NavigateToRegister -> {
                    navController.navigate(
                        Screen.Register.createRoute(event.name, event.email)
                    ) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            }
        }
    }

    SplashScreen()
}