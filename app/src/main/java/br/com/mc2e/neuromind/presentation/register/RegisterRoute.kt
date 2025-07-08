package br.com.mc2e.neuromind.presentation.register

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.mc2e.neuromind.presentation.navigation.Screen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RegisterRoute(
    navController: NavController,
    nameArg: String? = null,
    emailArg: String? = null
) {
    val viewModel: RegisterViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.preload(name = nameArg, email = emailArg)
        viewModel.uiEvent.collect { event ->
            when (event) {
                is RegisterUiEvent.NavigateToLogin -> navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Register.route) { inclusive = true }
                }

                is RegisterUiEvent.NavigateToHome -> navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Register.route) { inclusive = true }
                }

                else -> {}
            }
        }
    }


    AnimatedContent(
        targetState = uiState::class,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing),
                initialAlpha = 0.3f
            ) togetherWith fadeOut(
                animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing)
            )
        },
        label = "RegisterContentAnimation"
    ) { state ->

        when (state) {
            RegisterUiState.NameStep::class -> NameScreen(
                uiState = (uiState as? RegisterUiState.NameStep) ?: RegisterUiState.NameStep(),
                onEvent = viewModel::onEvent,
            )

            RegisterUiState.EmailStep::class ->
                EmailScreen(
                    uiState = (uiState as? RegisterUiState.EmailStep)
                        ?: RegisterUiState.EmailStep(),
                    onEvent = viewModel::onEvent,
                )

            RegisterUiState.PasswordStep::class ->
                //TODO: Criar tela de senha
                NameScreen(
                    uiState = (uiState as? RegisterUiState.NameStep) ?: RegisterUiState.NameStep(),
                    onEvent = viewModel::onEvent,
                )

            RegisterUiState.Success::class ->
                //TODO: Criar tela de sucesso
                NameScreen(
                    uiState = (uiState as? RegisterUiState.NameStep) ?: RegisterUiState.NameStep(),
                    onEvent = viewModel::onEvent,
                )

            RegisterUiState.Error::class ->
                //TODO: Criar tela de erro
                NameScreen(
                    uiState = (uiState as? RegisterUiState.NameStep) ?: RegisterUiState.NameStep(),
                    onEvent = viewModel::onEvent,
                )
        }
    }

}