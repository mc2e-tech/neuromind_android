package br.com.mc2e.neuromind.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import java.net.URLDecoder

fun NavGraphBuilder.composableWithArgs(
    route: String,
    optionalArgs: List<String>,
    content: @Composable (args: Map<String, String?>) -> Unit
) {
    composable(
        route = route,
        arguments = optionalArgs.map {
            navArgument(it) {
                nullable = true
                defaultValue = ""
            }
        }
    ) { backStackEntry ->
        val extractedArgs = optionalArgs.associateWith {key ->
            backStackEntry.arguments
                ?.getString(key)
                ?.takeIf { it.isNotBlank() }
                ?.let { URLDecoder.decode(it, Charsets.UTF_8.toString()) }
        }
        content(extractedArgs)
    }
}