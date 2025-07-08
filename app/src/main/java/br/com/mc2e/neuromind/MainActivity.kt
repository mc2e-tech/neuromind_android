package br.com.mc2e.neuromind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import br.com.mc2e.neuromind.presentation.navigation.AppNavGraph
import br.com.mc2e.neuromind.ui.theme.NeuroMindTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NeuroMindTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}