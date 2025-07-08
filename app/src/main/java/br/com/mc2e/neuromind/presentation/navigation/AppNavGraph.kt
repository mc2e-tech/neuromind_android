package br.com.mc2e.neuromind.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.mc2e.neuromind.presentation.auth.login.LoginRoute
import br.com.mc2e.neuromind.presentation.auth.splash.SplashRoute
import br.com.mc2e.neuromind.presentation.home.HomeRoute
import br.com.mc2e.neuromind.presentation.register.RegisterRoute

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(Screen.Splash.route) {
            SplashRoute(navController)
        }
        composable(Screen.Login.route) {
            LoginRoute(navController)
        }
        composable(Screen.Home.route) {
            HomeRoute(navController)
        }
        composableWithArgs(
            route = Screen.Register.route,
            optionalArgs = listOf("name", "email")
        ) { args ->
            RegisterRoute(
                navController = navController,
                nameArg = args["name"],
                emailArg = args["email"],
            )
        }
    }
}