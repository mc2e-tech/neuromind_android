package br.com.mc2e.neuromind.presentation.auth.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import br.com.mc2e.neuromind.R
import br.com.mc2e.neuromind.presentation.shared.components.buttons.PrimaryButton
import br.com.mc2e.neuromind.presentation.shared.navigation.Screen
import br.com.mc2e.neuromind.ui.theme.NeuroMindTheme
import br.com.mc2e.neuromind.ui.theme.mediumValue

@Composable
fun IntroScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController? = null
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(mediumValue)
            .background(Color.White),
        verticalArrangement = Arrangement.Center
    ) {
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.login),
            onClick = {
                navController?.navigate(Screen.Login.route) {
                    popUpTo(0)
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IntroScreenPreview() {
    NeuroMindTheme {
        IntroScreen()
    }
}