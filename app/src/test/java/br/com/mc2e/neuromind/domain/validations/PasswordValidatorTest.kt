package br.com.mc2e.neuromind.domain.validations

import br.com.mc2e.neuromind.domain.failures.ValidationException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class PasswordValidatorTest {
    @Test
    fun `validate should throw PasswordValidationException when password is empty`() {
        val exception = assertThrows(ValidationException.PasswordValidationException::class.java) {
            PasswordValidator.validate("")
        }
        assertEquals("Password cannot be empty", exception.message)
    }

    @Test
    fun `validate should throw PasswordValidationException when password is blank`() {
        val exception = assertThrows(ValidationException.PasswordValidationException::class.java) {
            PasswordValidator.validate("   ")
        }
        assertEquals("Password cannot be empty", exception.message)
    }

    @Test
    fun `validate should throw PasswordValidationException when password is too short`() {
        val exception = assertThrows(ValidationException.PasswordValidationException::class.java) {
            PasswordValidator.validate("Abc1!")
        }
        assertEquals("Password must be at least 8 characters long", exception.message)
    }

    @Test
    fun `validate should throw PasswordValidationException when password is too long`() {
        val longPassword = "A".repeat(101)
        val exception = assertThrows(ValidationException.PasswordValidationException::class.java) {
            PasswordValidator.validate(longPassword)
        }
        assertEquals("Password cannot be longer than 100 characters", exception.message)
    }

    @Test
    fun `validate should throw PasswordValidationException when password has no uppercase`() {
        val exception = assertThrows(ValidationException.PasswordValidationException::class.java) {
            PasswordValidator.validate("password123!")
        }
        assertEquals("Password must contain at least one uppercase letter", exception.message)
    }

    @Test
    fun `validate should throw PasswordValidationException when password has no lowercase`() {
        val exception = assertThrows(ValidationException.PasswordValidationException::class.java) {
            PasswordValidator.validate("PASSWORD123!")
        }
        assertEquals("Password must contain at least one lowercase letter", exception.message)
    }

    @Test
    fun `validate should throw PasswordValidationException when password has no number`() {
        val exception = assertThrows(ValidationException.PasswordValidationException::class.java) {
            PasswordValidator.validate("Password!")
        }
        assertEquals("Password must contain at least one number", exception.message)
    }

    @Test
    fun `validate should throw PasswordValidationException when password has no special char`() {
        val exception = assertThrows(ValidationException.PasswordValidationException::class.java) {
            PasswordValidator.validate("Password123")
        }
        assertEquals("Password must contain at least one special character", exception.message)
    }

    @Test
    fun `validate should throw PasswordValidationException when password has invalid chars`() {
        val exception = assertThrows(ValidationException.PasswordValidationException::class.java) {
            PasswordValidator.validate("Password123!@#$%^&*()_+{}|:<>?`~")
        }
        assertEquals("Password contains invalid characters", exception.message)
    }

    @Test
    fun `validate should not throw when password is valid`() {
        val validPasswords = listOf(
            "Password123!",
            "P@ssw0rd",
            "Test123!@#",
            "Complex1Pass!",
            "Abc123!@#$%^&*()_+-=[]{}|;:,.<>?"
        )

        validPasswords.forEach { password ->
            PasswordValidator.validate(password)
        }
    }
} 