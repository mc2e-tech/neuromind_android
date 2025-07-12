package br.com.mc2e.neuromind.presentation.auth.intro

sealed class IntroUserInputEvent {
    data object LoginClicked : IntroUserInputEvent()
    data object RegisterClicked : IntroUserInputEvent()
} 