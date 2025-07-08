package br.com.mc2e.neuromind.presentation.auth.login

sealed class LoginUiEvent {
    data object NavigateToHome : LoginUiEvent()
    data object NavigateToRegister : LoginUiEvent()
    data object NavigateToForgotPassword : LoginUiEvent()
    data object EmailFieldError : LoginUiEvent()
    data object ResetEmailError : LoginUiEvent()
    data object PasswordFieldError : LoginUiEvent()
    data object ResetPasswordError : LoginUiEvent()
    data object ShowGlobalError : LoginUiEvent()
} 