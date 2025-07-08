package br.com.mc2e.neuromind.presentation.auth.splash

sealed class SplashUiEvent {
    data object NavigateToHome : SplashUiEvent()
    data object NavigateToLogin : SplashUiEvent()
    data object NavigateToIntro : SplashUiEvent()
    data class NavigateToRegister(
        val name: String? = null,
        val email: String? = null
    ) : SplashUiEvent()
}