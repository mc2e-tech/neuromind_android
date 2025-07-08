package br.com.mc2e.neuromind.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.mc2e.neuromind.presentation.shared.navigation.Screen

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


    when (uiState) {
        is RegisterUiState.NameStep -> NameScreen(
            uiState = uiState as RegisterUiState.NameStep,
            onEvent = viewModel::onEvent,
        )

        is RegisterUiState.EmailStep ->
            //TODO: Criar tela de email
            NameScreen(
                uiState = RegisterUiState.NameStep(),
                onEvent = viewModel::onEvent,
            )

        is RegisterUiState.PasswordStep ->
            //TODO: Criar tela de senha
            NameScreen(
                uiState = RegisterUiState.NameStep(),
                onEvent = viewModel::onEvent,
            )

        is RegisterUiState.Success ->
            //TODO: Criar tela de sucesso
            NameScreen(
                uiState = RegisterUiState.NameStep(),
                onEvent = viewModel::onEvent,
            )

        is RegisterUiState.Error ->
            //TODO: Criar tela de erro
            NameScreen(
                uiState = RegisterUiState.NameStep(),
                onEvent = viewModel::onEvent,
            )
    }


}