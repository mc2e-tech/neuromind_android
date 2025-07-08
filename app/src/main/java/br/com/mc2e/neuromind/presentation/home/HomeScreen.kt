import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import br.com.mc2e.neuromind.R
import br.com.mc2e.neuromind.presentation.auth.login.LoginUiEvent
import br.com.mc2e.neuromind.presentation.auth.login.LoginUiState
import br.com.mc2e.neuromind.presentation.auth.login.LoginUserInputEvent
import br.com.mc2e.neuromind.presentation.auth.login.components.ForgotPasswordButton
import br.com.mc2e.neuromind.presentation.components.buttons.PrimaryButton
import br.com.mc2e.neuromind.presentation.navigation.Screen
import br.com.mc2e.neuromind.ui.theme.NeuroMindTheme
import br.com.mc2e.neuromind.ui.theme.horizontalPaddingValue
import br.com.mc2e.neuromind.ui.theme.mediumValue
import br.com.mc2e.neuromind.ui.theme.verticalMediumPadding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
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
            text = stringResource(R.string.back),
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
fun HomeScreenPreview() {
    NeuroMindTheme {
        HomeScreen()
    }
}