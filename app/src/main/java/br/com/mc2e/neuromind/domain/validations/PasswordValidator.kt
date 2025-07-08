package br.com.mc2e.neuromind.domain.validations

import br.com.mc2e.neuromind.domain.failures.ValidationException

class PasswordValidator {
    companion object {
        private const val MIN_LENGTH = 8
        private const val MAX_LENGTH = 100

        private const val ERROR_EMPTY = "Password cannot be empty"
        private const val ERROR_TOO_SHORT = "Password must be at least 8 characters long"
        private const val ERROR_TOO_LONG = "Password cannot be longer than 100 characters"
        private const val ERROR_NO_UPPERCASE = "Password must contain at least one uppercase letter"
        private const val ERROR_NO_LOWERCASE = "Password must contain at least one lowercase letter"
        private const val ERROR_NO_NUMBER = "Password must contain at least one number"
        private const val ERROR_NO_SPECIAL_CHAR = "Password must contain at least one special character"
        private const val ERROR_INVALID_CHARS = "Password contains invalid characters"

        private val UPPERCASE_REGEX = Regex("[A-Z]")
        private val LOWERCASE_REGEX = Regex("[a-z]")
        private val NUMBER_REGEX = Regex("[0-9]")
        private val SPECIAL_CHAR_REGEX = Regex("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]")
        private val VALID_CHAR_REGEX = Regex("^[A-Za-z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+$")

        fun validate(value: String) {
            if (value.isBlank() || value.trim().isEmpty()) {
                throw ValidationException.PasswordValidationException(ERROR_EMPTY)
            }

            if (!VALID_CHAR_REGEX.matches(value)) {
                throw ValidationException.PasswordValidationException(ERROR_INVALID_CHARS)
            }

            if (value.length < MIN_LENGTH) {
                throw ValidationException.PasswordValidationException(ERROR_TOO_SHORT)
            }

            if (value.length > MAX_LENGTH) {
                throw ValidationException.PasswordValidationException(ERROR_TOO_LONG)
            }

            if (!UPPERCASE_REGEX.containsMatchIn(value)) {
                throw ValidationException.PasswordValidationException(ERROR_NO_UPPERCASE)
            }

            if (!LOWERCASE_REGEX.containsMatchIn(value)) {
                throw ValidationException.PasswordValidationException(ERROR_NO_LOWERCASE)
            }

            if (!NUMBER_REGEX.containsMatchIn(value)) {
                throw ValidationException.PasswordValidationException(ERROR_NO_NUMBER)
            }

            if (!SPECIAL_CHAR_REGEX.containsMatchIn(value)) {
                throw ValidationException.PasswordValidationException(ERROR_NO_SPECIAL_CHAR)
            }
        }
    }
}