package br.com.mc2e.neuromind.presentation.navigation

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Intro : Screen("intro")
    data object Login : Screen("login")
    data object Home : Screen("home")
    data object Register : Screen("register?name={name}&email={email}") {
        fun createRoute(name: String? = null, email: String? = null): String {
            val encodedName =
                name?.let { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) } ?: ""

            val encodedEmail =
                email?.let { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) } ?: ""

            return "register?name=$encodedName&email=$encodedEmail"
        }
    }
}