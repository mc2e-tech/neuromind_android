package br.com.mc2e.neuromind.domain.validations

import br.com.mc2e.neuromind.domain.failures.ValidationException

class EmailValidator {
    companion object {
        private const val MAX_LENGTH = 255

        private const val ERROR_EMPTY = "Email cannot be empty"
        private const val ERROR_TOO_LONG = "Email cannot be longer than 255 characters"
        private const val ERROR_INVALID_FORMAT = "Invalid email format"

        // Regex para caracteres válidos na parte local do email
        private val LOCAL_PART_REGEX = Regex("^[A-Za-z0-9.!#$%&'*+/=?^_`{|}~-]+$")

        fun validate(value: String) {
            if (value.isBlank()) {
                throw ValidationException.EmailValidationException(ERROR_EMPTY)
            }

            if (value.length > MAX_LENGTH) {
                throw ValidationException.EmailValidationException(ERROR_TOO_LONG)
            }

            // No spaces allowed
            if (value.contains(" ")) {
                throw ValidationException.EmailValidationException(ERROR_INVALID_FORMAT)
            }

            // Only one @ allowed
            if (value.count { it == '@' } != 1) {
                throw ValidationException.EmailValidationException(ERROR_INVALID_FORMAT)
            }

            val parts = value.split("@")
            val local = parts[0]
            val domain = parts[1]

            if (local.isEmpty() || domain.isEmpty()) {
                throw ValidationException.EmailValidationException(ERROR_INVALID_FORMAT)
            }

            // Local part character validation
            if (!LOCAL_PART_REGEX.matches(local)) {
                throw ValidationException.EmailValidationException(ERROR_INVALID_FORMAT)
            }

            // Local part checks
            if (local.startsWith(".") || local.endsWith(".")) {
                throw ValidationException.EmailValidationException(ERROR_INVALID_FORMAT)
            }
            if (local.contains("..")) {
                throw ValidationException.EmailValidationException(ERROR_INVALID_FORMAT)
            }

            // Domain part checks
            if (domain.startsWith("-") || domain.endsWith("-")) {
                throw ValidationException.EmailValidationException(ERROR_INVALID_FORMAT)
            }
            if (domain.startsWith(".") || domain.endsWith(".")) {
                throw ValidationException.EmailValidationException(ERROR_INVALID_FORMAT)
            }
            if (domain.contains("..")) {
                throw ValidationException.EmailValidationException(ERROR_INVALID_FORMAT)
            }

            // Domain parts validation
            val domainParts = domain.split(".")
            if (domainParts.size < 2) {
                throw ValidationException.EmailValidationException(ERROR_INVALID_FORMAT)
            }

            if (domainParts.any { it.isEmpty() || it.startsWith("-") || it.endsWith("-") }) {
                throw ValidationException.EmailValidationException(ERROR_INVALID_FORMAT)
            }

            // TLD check (last part after last dot)
            val tld = domainParts.lastOrNull()
            if (tld == null || tld.length < 2 || tld.any { !it.isLetter() }) {
                throw ValidationException.EmailValidationException(ERROR_INVALID_FORMAT)
            }
        }
    }
}