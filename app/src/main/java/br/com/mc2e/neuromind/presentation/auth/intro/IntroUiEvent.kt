package br.com.mc2e.neuromind.presentation.auth.intro

sealed class IntroUiEvent {
    data object NavigateToLogin : IntroUiEvent()
    data object NavigateToRegister : IntroUiEvent()
}