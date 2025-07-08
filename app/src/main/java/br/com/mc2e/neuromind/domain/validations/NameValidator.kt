package br.com.mc2e.neuromind.domain.validations

import br.com.mc2e.neuromind.domain.failures.ValidationException

class NameValidator {
    companion object {
        private const val MAX_LENGTH = 100
        private const val MIN_LENGTH = 4
        private const val MIN_WORDS = 2
        private const val ERROR_EMPTY = "Name is required"
        private const val ERROR_TOO_LONG = "Name must be at most $MAX_LENGTH characters long"
        private const val ERROR_TOO_SHORT = "Name must be at least $MIN_LENGTH characters short"
        private const val ERROR_INVALID_FORMAT = "Name contains invalid characters. Only letters, spaces, hyphens, and apostrophes are allowed"
        private val SPECIAL_CHAR_REGEX = Regex("^[a-zA-ZÀ-ÿ\\s'-]+$")

        fun validate(value: String) {
            if (value.isBlank()) {
                throw ValidationException.NameValidationException(ERROR_EMPTY)
            }

            val trimmedValue = value.trim()
            if (trimmedValue.length < MIN_LENGTH) {
                throw ValidationException.NameValidationException(ERROR_TOO_SHORT)
            }
            if (trimmedValue.length > MAX_LENGTH) {
                throw ValidationException.NameValidationException(ERROR_TOO_LONG)
            }

            if (!SPECIAL_CHAR_REGEX.matches(trimmedValue)) {
                throw ValidationException.NameValidationException(ERROR_INVALID_FORMAT)
            }

            // Check if name has at least two words (first name and surname)
            val words = trimmedValue.split("\\s+".toRegex())
            if (words.size < MIN_WORDS) {
                throw ValidationException.CompleteNameValidationException()
            }
        }
    }
}