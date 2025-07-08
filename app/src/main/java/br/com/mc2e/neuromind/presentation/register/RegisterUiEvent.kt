package br.com.mc2e.neuromind.presentation.register

sealed class RegisterUiEvent {
    data object NavigateToHome : RegisterUiEvent()
    data object NavigateToLogin : RegisterUiEvent()
}