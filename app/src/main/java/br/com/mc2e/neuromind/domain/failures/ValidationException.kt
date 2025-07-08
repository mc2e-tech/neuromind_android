package br.com.mc2e.neuromind.domain.failures

sealed class ValidationException(message: String) : Exception(message) {
    class EmailValidationException(message: String) : ValidationException(message)
    class PasswordValidationException(message: String) : ValidationException(message)
    class NameValidationException(message: String) : ValidationException(message)
    class CompleteNameValidationException() : ValidationException("")
}