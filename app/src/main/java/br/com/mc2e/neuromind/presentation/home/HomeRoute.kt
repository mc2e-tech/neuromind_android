package br.com.mc2e.neuromind.presentation.home

import HomeScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun HomeRoute(navController: NavHostController) {
    HomeScreen(
        navController = navController
    )
}