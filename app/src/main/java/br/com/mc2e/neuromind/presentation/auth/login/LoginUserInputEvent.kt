package br.com.mc2e.neuromind.presentation.auth.login

sealed class LoginUserInputEvent {
    data class EmailChanged(val email: String) : LoginUserInputEvent()
    data class PasswordChanged(val password: String) : LoginUserInputEvent()
    data object PasswordFieldFocusRequested : LoginUserInputEvent()
    data object Submit : LoginUserInputEvent()
    data object ForgotPasswordClicked : LoginUserInputEvent()
    data object RegisterClicked : LoginUserInputEvent()
} 