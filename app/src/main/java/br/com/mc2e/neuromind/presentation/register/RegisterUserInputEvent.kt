package br.com.mc2e.neuromind.presentation.register

sealed class RegisterUserInputEvent {
    data class NameChanged(val value: String) : RegisterUserInputEvent()
    data class EmailChanged(val value: String) : RegisterUserInputEvent()
    data class PasswordChanged(val value: String) : RegisterUserInputEvent()
    data class ConfirmPasswordChanged(val value: String) : RegisterUserInputEvent()
    object NextStepClicked : RegisterUserInputEvent()
    object OnBack : RegisterUserInputEvent()
    object LoginClicked : RegisterUserInputEvent()
    object EnterClicked : RegisterUserInputEvent()
    object TryAgainClicked : RegisterUserInputEvent()
}