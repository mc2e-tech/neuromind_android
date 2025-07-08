package br.com.mc2e.neuromind.presentation.auth.login

sealed class LoginUiState {
    data class Form(
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
    ) : LoginUiState()
    data object Success : LoginUiState()
}