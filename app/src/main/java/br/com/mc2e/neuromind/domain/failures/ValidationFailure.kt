package br.com.mc2e.neuromind.domain.failures

sealed class ValidationFailure : Failure() {
    data class InvalidEmail(val message: String) : ValidationFailure()
    data class InvalidPassword(val message: String) : ValidationFailure()
    data object InvalidCompleteName : ValidationFailure()
    data object InvalidName : ValidationFailure()
    data object ConfirmPasswordMismatchError : ValidationFailure()
}